import threading
import queue
from collections import defaultdict

class AnswerBuffer:
    def __init__(self, size):
        self.mutex = threading.Lock()
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.waitingReqs = threading.Semaphore(0)
        self.list = []
        self.condition = threading.Condition(self.mutex)

    def add(self, item):
        self.spaces.acquire()
        with self.mutex:
            self.list.append(item)
            print(str(item[0]) + " ADD ANSWER >  " + str(item[2]) + "\n")
            self.condition.notify_all()

        #self.items.release()

    def remove(self, reqData): #TODO: removerako erantzunak buzoietan jarriko dia. Erabiltzaileak bere erantzunak jasoteko in bida. Monitore batekin ahal da iñ buzoi danak beiketako beria topau arte(ez efizientia)
        #Bestela remove-ian semaforo bat sartu erabiltzaile batek bere erantzuna bakarrik hartzeko.
        item = 0

        with self.mutex:
            #self.items.acquire()
            while True:
                index = self.findRequestByID(reqData)
                if index is not None:
                    item = self.list.pop(index)
                    print(str(item[0]) + " TAKE ANSWER < " + str(item[2]))
                    self.spaces.release()
                    return item

                print(f"Request {reqData} not found. Waiting...")
                self.condition.wait()
        """
        try:
            self.mutex.acquire()
        except InterruptedError:
            self.items.release()
            print("Something went wrong on the remove function")

        self.items.acquire()

        
        while index == None:
            self.waitingReqs.acquire()
            print(str(self.list))
            index = self.findRequestByID(reqData)

        #itemIndex = self.findRequestByID(reqData)
        #if itemIndex != None:
        item = self.list.pop(index)

        print(str(item[0]) + " TAKE ANSWER < " + str(item[2]) + "\n")

        self.mutex.release()
        self.spaces.release()
        return item"""

    def findRequestByID(self, reqData):
        for i in range(len(self.list)):
            if self.list[i][0] == reqData[0] and self.list[i][1] == reqData[1]:
                print(f"INDEX OF ORIGINAL ELEMENT: {i} {reqData}")
                return i
        return None

    def show(self):
        return str(self.list)

    def isNotEmpty(self):
        return not (len(self.list) == 0)