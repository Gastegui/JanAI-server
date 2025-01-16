import threading
import queue
from collections import defaultdict
import time

#QueryBuffer eta AnswerBuffer fitxategi berdiñian ahalko zian eon. Klase ezberdiñak definitu ahalko zian

class QueryBuffer:
    def __init__(self, size):
        self.mutex = threading.Semaphore(1)
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.ready = threading.Semaphore(0)
        self.ready = threading.Semaphore(0)
        self.bq = queue.Queue(maxsize=size)

        self.add_timeout = 1
        self.allow_additions = True  # Flag to control adding items
        self.timer_thread = None

    def start_timer(self):
        self.allow_additions = True
        self.timer_thread = threading.Timer(self.add_timeout, self.stop_additions)
        self.timer_thread.start()

    def stop_additions(self):
        self.allow_additions = False
        print("NO MORE REQUESTS FOR NOW --------------------------------------------------------------------------")
        self._wait_for_empty()

    def _wait_for_empty(self):
        while not self.bq.empty():
            time.sleep(0.1)  # Check periodically
        print("Queue is now empty. Additions are allowed again.")
        self.start_timer()

    def add(self, reqData):
        if not self.allow_additions:
            return 
        self.spaces.acquire()

        try:
            self.mutex.acquire()
        except InterruptedError:
            self.spaces.release()
            print("Something went wrong on the add function")
        self.bq.put(reqData)
        #print(list(self.bq.queue))

        print(str(reqData[0]) + " ADD QUERY >  " + str(reqData[2]) + "\n")

        self.mutex.release()
        self.items.release()

        if self.bq.full() or self.allow_additions == False: #or timeout reached
            self.ready.release(self.bq.qsize()) #self.bq.size 5 TODO: Hau beste modu batera jarri
        self.ready.acquire()

    def remove(self):
        item = 0
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

        return item

    def show(self):
        return list(self.bq.queue)

    def isNotEmpty(self):
        return not (len(self.bq) == 0)