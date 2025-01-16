import threading
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
import time

class Answer(threading.Thread):
    def __init__(self, QBuffer, ABuffer):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.userID = 0

    def run(self):
        while not self._stop_event.is_set():
            try:
                item = self.query.remove()

                if item[2] != 0:
                    time.sleep(1/2)
                    self.answer.add(item)
                else:
                    self.interrupt()
                    break

            except InterruptedError as e:
                print(f"Error in ANSWER thread {self.name}: {e}")
                self.end = True
                break

    def interrupt(self):
        self._stop_event.set()