import random
import time
import threading
import ChatMain 

class Premium(threading.Thread):
    def __init__(self, chatMain):
        super().__init__()
        self.chatMain = chatMain
        self._stop_event = threading.Event()
        self.rand = 0

    def run(self):
        while not self._stop_event.is_set():
            try:
                # Use a short, interruptible sleep
                self._stop_event.wait(timeout=1)
                if not self._stop_event.is_set():
                    ChatMain.ChatMain.helpChat(self.chatMain)
            except Exception as e:
                print(f"Error in Premium thread {self.name}: {e}")
                self.stop()
                break

    def stop(self):
        self._stop_event.set()

    def pullSleigh():
        print('Premium is chatting with NutriChat')
        time.sleep(random.random(0, 3/100) + 1)