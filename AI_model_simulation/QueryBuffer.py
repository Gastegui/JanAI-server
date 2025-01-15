import threading
from ReusableBarrier import ReusableBarrier
import queue
from collections import defaultdict

class QueryBuffer:
    def __init__(self, size):
        self.mutex = threading.Semaphore(1)
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.ready = threading.Semaphore(0)
        self.reusableBarrier = ReusableBarrier(size)
        self.ready = threading.Semaphore(0)
        self.bq = queue.Queue(maxsize=size)
        self.userItemMap = defaultdict(list)

    def add(self, item, userID):
        self.spaces.acquire()

        try:
            self.mutex.acquire()
        except InterruptedError:
            self.spaces.release()
            print("Something went wrong on the add function")
        self.bq.put(item)
        self.userItemMap[userID].append(item)

        print(str(userID) + " ADD QUERY >  " + str(item) + "\n")

        self.mutex.release()
        self.items.release()

        if self.bq.full():
            self.ready.release(5)
        self.ready.acquire()


    def remove(self):
        item = 0
        userID = 0
        if not self.bq.empty():
            self.items.acquire()
            try:
                self.mutex.acquire()
            except InterruptedError:
                self.items.release()
                print("Something went wrong on the remove function")

            item = self.bq.get()
            print(list(self.bq.queue))

            userID = self.find_user_id(item, self.userItemMap)
            
            print(str(userID) + " TAKE QUERY < " + str(item) + "\n")
            self.userItemMap[userID].remove(item)

            self.mutex.release()
            self.spaces.release()
        return item, userID

    def show(self):
        return list(self.bq.queue)

    def isNotEmpty(self):
        return not (len(self.bq) == 0)
    
    def find_user_id(self, item_to_find, userItemMap):
        for user_id, items in userItemMap.items():
            if item_to_find in items:
                return user_id
        return None