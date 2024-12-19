import threading
import ChatMain
import random
import time

class User(threading.Thread):
    def __init__(self, chatMain):
        super().__init__()
        self.chatMain = chatMain
        self._stop_event = threading.Event()
        self.rand = 0

    def run(self):
        while not self._stop_event.is_set():
            try:
                time.sleep(1)
                ChatMain.ChatMain.askForHelp(self.chatMain)
            except InterruptedError:
                self.stop()
    
    def stop(self):
        self._stop_event.set()

    def getHelp(self):
        print('User is chatting with NutriChat')
        time.sleep(random.random(0, 1/100) + 6/10)