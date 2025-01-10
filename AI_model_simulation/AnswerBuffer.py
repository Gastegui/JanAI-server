import threading

class AnswerBuffer:
    def __init__(self, size):
        self.mutex = threading.Semaphore(1)
        self.items = threading.Semaphore(0)
        self.spaces = threading.Semaphore(size)
        self.list = []

    def add(self, item):
        self.spaces.acquire()
        try:
            self.mutex.acquire()
        except InterruptedError:
            self.spaces.release()
            print("Something went wrong on the add function")
        self.list.append(item)
        print("ADD ANSWER >  " + str(item))
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
        print("TAKE ANSWER < " + str(item))
        self.mutex.release()
        self.spaces.release()
        return item

    def show(self):
        return str(self.list)

    def isNotEmpty(self):
        return not (len(self.list) == 0)