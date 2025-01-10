import threading
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
import random

class User(threading.Thread):
    def __init__(self, QBuffer, ABuffer):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.end = False

    def run(self):
        #while not self._stop_event.is_set() or self.end or QueryBuffer.isNotEmpty(self.NewBuffer):
        try:
            rand = random.randint(0, 50)
            QueryBuffer.add(self.query, rand)

            AnswerBuffer.remove(self.answer)


        except InterruptedError as e:
            print(f"Error in USER thread {self.name}: {e}")
            self.end = True
            #break

    def interrupt(self):
        self._stop_event.set()