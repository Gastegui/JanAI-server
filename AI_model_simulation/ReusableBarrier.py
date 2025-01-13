import threading

class ReusableBarrier:

    def __init__(self, numThreads):
        self.numThreads = numThreads
        self.numWaiting = 0
        self.turnstile = threading.Semaphore(1)
        self.turnstile2 = threading.Semaphore(0)
        self.mutex = threading.Semaphore(1)
    
    def waitBarrier(self):

        self.turnstile.acquire()
        self.turnstile.release()
        self.mutex.acquire()
        self.numWaiting += 1
        if (self.numWaiting == self.numThreads):
            self.turnstile.acquire()
            self.turnstile2.release()
        
        self.mutex.release()

        self.turnstile2.acquire()
        self.turnstile2.release()

        self.mutex.acquire()
        self.numWaiting -= 1
        if (self.numWaiting == 0):
            self.turnstile2.acquire()
            self.turnstile.release()
        
        self.mutex.release()
    
