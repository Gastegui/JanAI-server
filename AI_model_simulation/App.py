import time
from AI_model import Model
from User import User
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
#TODO: Start timeout when first reqeuest is added to queue
#TODO: The model shouldn't take any requests while the query buffer is empty
#TODO: Fix ending of program

class App:
    def __init__(self):
        self.QBuffer = QueryBuffer(5)
        self.ABuffer = AnswerBuffer(5)

        self.model = Model(self.QBuffer, self.ABuffer)

        self.users = []
        for i in range(5):
            self.users.append(User(self.QBuffer, self.ABuffer, i))

    def start_threads(self):        
        for user in self.users:
            user.sendRequests()
        
        self.model.answerRequests()

    def interrupt_threads(self):
        #self.QBuffer.kill_buffer()
        for user in self.users:
            user.interruptRequests()
            try:
                user.joinThreads()
            except InterruptedError as e:
                e.with_traceback()

        self.model.interruptAnswer()
        try:
            self.model.joinThreads()
        except InterruptedError as e:
            e.with_traceback()
        
        print(self.QBuffer.show())
        print(self.ABuffer.show())

    def run(self):
        self.start_threads()

        try:
            time.sleep(20)
        except InterruptedError as e:
            e.with_traceback()

        self.interrupt_threads()

if __name__ == "__main__":
    app = App()
    app.run()
