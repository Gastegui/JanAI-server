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
        while not self._stop_event.is_set(): #and not self.query.isEmpty()
            try:
                item = self.query.remove()

                if item == None:
                    self.interrupt()
                    break
                elif item[0] != -1:
                    time.sleep(0.5)
                    self.answer.add(item)
                

            except InterruptedError as e:
                print(f"Error in ANSWER thread {self.name}: {e}")
                break

    def interrupt(self):
        self._stop_event.set()