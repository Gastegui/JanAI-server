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
                if item != None:
                    time.sleep(1)
                    self.answer.add(item)
                
            except InterruptedError as e:
                print(f"Error in ANSWER thread {self.name}: {e}")
                break

    def interrupt(self):
        self.query.kill_buffer()
        self._stop_event.set()