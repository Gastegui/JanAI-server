import threading
import queue
import time

#QueryBuffer eta AnswerBuffer fitxategi berdiñian ahalko zian eon. Klase ezberdiñak definitu ahalko zian

class QueryBuffer:
    def __init__(self, size):
        self.mutex = threading.Semaphore(1)
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.ready = threading.Semaphore(0)
        self.waiting = threading.Semaphore(0)
        self.bq = queue.Queue(maxsize=size)
        self.modelReady = threading.Semaphore(0)
        self.waitingCount = 0

        self.add_timeout = 2
        self.allow_additions = True
        self.timer_thread = None
        self.released = False

    def start_timer(self):
        self.allow_additions = True
        self.timer_thread = threading.Timer(self.add_timeout, self.stop_additions)
        self.timer_thread.start()

    def stop_timer(self):
        self.allow_additions = False
        self.timer_thread.cancel()

    def stop_additions(self):
        self.allow_additions = False
        print("REQUESTS ARE NOT ALLOWER UNTIL QUEUE IS EMPTY AGAIN ------------------------------------------------------- queue size: " + str(self.bq.qsize()))
        self._wait_for_empty()

    def _wait_for_empty(self):
        while not self.bq.empty():
            time.sleep(0.1)  # Check periodically
        print("Queue is now empty. Additions are allowed again.")
        self.start_timer()

    def add(self, reqData):
        self.spaces.acquire()

        try:
            self.mutex.acquire()
        except InterruptedError:
            self.spaces.release()
            print("Something went wrong on the add function")
        
        if not self.allow_additions or self.bq.full():
            #print("THREAD IS GOING TO BLOCKING STATE")

            self.waitingCount += 1
            self.mutex.release()
            #print("AMOUNT OF WAITING THREADS: " + str(self.waitingCount))
            self.waiting.acquire()            

        self.bq.put(reqData)
     
        print(str(reqData[0]) + " ADD QUERY >  " + str(reqData[2]) + "\n")

        self.mutex.release()
        self.items.release()

        self.mutex.acquire()
        if (self.bq.full() and not self.released) or (not self.allow_additions and not self.released): #or timeout reached
            self.released = True
            #print("THE VALUE OF BOOLEAN RELEASED: -------------------------------------- " + str(self.released))

            self.stop_timer()
            print("Releasing the threads that are ready..." + str(self.bq.qsize()))
            self.ready.release(self.bq.qsize()) #TODO: Hau beste modu batera jarri
            self.modelReady.release()

            self.mutex.release()
        else:
            
            self.mutex.release()
            self.ready.acquire()

    def remove(self):
        item = None
        if not self.bq.empty(): #TODO: kondizio hau mutex batekin babestu
            self.items.acquire()
            try:
                self.mutex.acquire()
            except InterruptedError:
                self.items.release()
                print("Something went wrong on the remove function")
            
            item = self.bq.get()
            
            print(str(item[0]) + " TAKE QUERY < " + str(item[2]) + "\n")
            self.mutex.release()
            self.spaces.release()
            
        else:
            if self.waitingCount >= 1:
                self.released = False
                print("RELEASING " + str(self.waitingCount) + " WAITING THREADS")
                self.waiting.release(self.waitingCount) #maxsize of the queue
                self.waitingCount = 0
            print("******************************************************************\n******************************************************************")
            self.start_timer()
            self.modelReady.acquire()

        return item

    def show(self):
        return list(self.bq.queue)

    def isNotEmpty(self):
        return not (len(self.bq) == 0)