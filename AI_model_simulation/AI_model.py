import threading
from Answer import Answer
import random

class Model(threading.Thread):
    def __init__(self, QBuffer, ABuffer):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.mainAnswer = None

    def answerRequests(self):
        self.mainAnswer = Answer(self.query, self.answer)
        self.mainAnswer.start()

    def interruptAnswer(self):
        self.mainAnswer.interrupt()

    def joinThreads(self):
        self.mainAnswer.join()