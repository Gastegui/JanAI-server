import threading
import queue
import time

#QueryBuffer eta AnswerBuffer fitxategi berdiñian ahalko zian eon. Klase ezberdiñak definitu ahalko zian

class QueryBuffer:
    def __init__(self, size):
        self.mutex = threading.Lock()
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.ready = threading.Semaphore(0)
        self.waiting = threading.Semaphore(0)
        self.bq = queue.Queue(maxsize=size)
        self.waitingCount = 0
        self.condition = threading.Condition(self.mutex)

        self.end = False
        self.released = False

    def kill_buffer(self):
        self.end = True
        with self.mutex:
            self.condition.notify()

    def add(self, reqData):
        self.spaces.acquire()
        with self.mutex:
            self.bq.put(reqData)
            print(str(reqData[0]) + " ADD QUERY >  " + str(reqData[2]) + "\n")
            if self.bq.full():
                self.condition.notify()

    def remove(self): #TODO: removerako erantzunak buzoietan jarriko dia. Erabiltzaileak bere erantzunak jasoteko in bida. Monitore batekin ahal da iñ buzoi danak beiketako beria topau arte(ez efizientia)
        #Bestela remove-ian semaforo bat sartu erabiltzaile batek bere erantzuna bakarrik hartzeko.
        item = 0

        with self.mutex:
            #self.items.acquire()
            if self.bq.empty():
                self.spaces.release(5)
                self.condition.wait()
            if self.end == True:
                return None
            
            item = self.bq.get()
            print(str(item[0]) + " TAKE QUERY < " + str(item[2]) + "\n")
                
            return item

    def show(self):
        return list(self.bq.queue)

    def isNotEmpty(self):
        return not (len(self.bq) == 0)

    def isEmpty(self):
        return self.bq.empty()