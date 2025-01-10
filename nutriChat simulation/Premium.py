import threading
from ChatMain import ChatMain
import random
import time

class Premium(threading.Thread):
    def __init__(self, chatMain):
        super().__init__()
        self.chatMain = chatMain
        self._stop_event = threading.Event()
        self.rand = 0

    def run(self):
        while not self._stop_event.is_set():
            try:
                time.sleep(1)
                ChatMain.helpChat(self.chatMain)
            except Exception as e:
                print(f"Error in Premium thread {self.name}: {e}")
                break

    def pullSleigh():
        print('Premium is chatting with NutriChat')
        time.sleep(random.random(0, 3/100) + 1)

    def interrupt(self):
        self._stop_event.set()