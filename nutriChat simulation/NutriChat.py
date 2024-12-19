from ChatMain import ChatMain
import threading

class NutriChat(threading.Thread):

    def __init__(self, chatMain):
        super().__init__()
        self.chatMain = chatMain
        self._stop_event = threading.Event()

    def run(self):
        while not self._stop_event.is_set():
            try:
                ChatMain.beChat(self.chatMain)
            except InterruptedError:
                self.stop()
    
    def stop(self):
        self._stop_event.set()
    """
    def answerPremium():
        print("🎅 NutriChat is ANSWERING to Premium users")

    def finishPremium():
        print("🎅 NutriChat FINISHED with Premium users")
    
    def answerUser():
        print("💈 NutriChat HELPING normal users")
    
    def finishUser():
        print("💈 NutriChat STOPS helping")
    """