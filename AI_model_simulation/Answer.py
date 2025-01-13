import threading
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
import random

class Answer(threading.Thread):
    def __init__(self, QBuffer, ABuffer):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.end = False
        self.userID = 0

    def run(self):
        while not self._stop_event.is_set() or self.end:
            try:
                item, self.userID = QueryBuffer.remove(self.query)
                AnswerBuffer.add(self.answer, item, self.userID)

            except InterruptedError as e:
                print(f"Error in ANSWER thread {self.name}: {e}")
                self.end = True
                break

    def interrupt(self):
        self._stop_event.set()