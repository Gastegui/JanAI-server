import threading
import time
from AI_model import Model
from User import User
from QueryBuffer import QueryBuffer

class App:
    def __init__(self):
        self.buffer = QueryBuffer(10)

        self.producer = Model(self.buffer)
        self.consumer = User(self.buffer)

    def start_threads(self):
        self.producer.start()
        self.consumer.start()

    def interrupt_threads(self):
        self.producer.interrupt()
        try:
            self.producer.join()
        except InterruptedError as e:
            e.with_traceback()

        self.consumer.interrupt()
        try:
            self.consumer.join()
        except InterruptedError as e:
            e.with_traceback()
        print(self.buffer.show())

    def run(self):
        self.start_threads()

        try:
            time.sleep(1/100)
        except InterruptedError as e:
            e.with_traceback()

        self.interrupt_threads()

if __name__ == "__main__":
    app = App()
    app.run()
