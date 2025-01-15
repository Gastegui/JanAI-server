import threading
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
import random

class Request(threading.Thread):
    def __init__(self, QBuffer, ABuffer, id):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.end = False
        self.userID = id

    def run(self):
        #while not self._stop_event.is_set() or self.end:
        try:
            rand = random.randint(1, 50)
            QueryBuffer.add(self.query, rand, self.userID)

            item, userID = AnswerBuffer.remove(self.answer)
            
            print(str(userID) + " RECEIVED ANSWER FROM ANSWERBUFFER: " + str(item) + "\n")

        except InterruptedError as e:
            print(f"Error in REQUEST thread {self.name}: {e}")
            self.end = True
            #break

    def interrupt(self):
        self._stop_event.set()