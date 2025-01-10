import threading
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
import random

class Model(threading.Thread):
    def __init__(self, QBuffer, ABuffer):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.end = False

    def run(self):
        #while not self._stop_event.is_set():
        try:
            QueryBuffer.remove(self.query)

            rand = random.randint(0, 50)
            AnswerBuffer.add(self.answer, rand)

        except InterruptedError as e:
            print(f"Error in Producer thread {self.name}: {e}")
            self.interrupt()
            #break

    def interrupt(self):
        self._stop_event.set()