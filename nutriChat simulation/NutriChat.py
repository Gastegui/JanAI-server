from ChatMain import ChatMain
import threading

class NutriChat(threading.Thread):

    def __init__(self, chatMain):
        super().__init__()
        self.chatMain = chatMain
        self._stop_event = threading.Event()
        self.finished = False

    def run(self):
        while not self._stop_event.is_set():
            try:
                ChatMain.beChat(self.chatMain)
            except InterruptedError:
                print("NUTRICHAT STOPPED FOR SOME REASON")
                self.stop()
    
    def stop(self):
        self._stop_event.set()