import threading
import queue
from collections import defaultdict

class AnswerBuffer:
    def __init__(self, size):
        self.mutex = threading.Semaphore(1)
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.list = []
        self.bq = queue.Queue(maxsize=size)
        self.userItemMap = defaultdict(list)

    def add(self, item, userID):
        self.userID = userID
        self.spaces.acquire()
        try:
            self.mutex.acquire()
        except InterruptedError:
            self.spaces.release()
            print("Something went wrong on the add function")
        self.list.append(item)
        self.userItemMap[userID].append(item)

        print(str(userID) + " ADD ANSWER >  " + str(item) + "\n")
        self.mutex.release()
        self.items.release()

    def remove(self):
        item = 0
        self.items.acquire()
        try:
            self.mutex.acquire()
        except InterruptedError:
            self.items.release()
            print("Something went wrong on the remove function")
        item = self.list.pop(0)
        #item = self.bq.get()
        userID = self.find_user_id(item, self.userItemMap)

        print(str(userID) + " TAKE ANSWER < " + str(item) + "\n")
        self.userItemMap[userID].remove(item)

        self.mutex.release()
        self.spaces.release()
        return item, userID

    def show(self):
        return str(self.list)
        #return str(self.bq)

    def isNotEmpty(self):
        return not (len(self.list) == 0)
    
    def find_user_id(self, item_to_find, userItemMap):
        for user_id, items in userItemMap.items():
            if item_to_find in items:
                return user_id
        return None