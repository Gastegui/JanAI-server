import time
from NutriChat import NutriChat
from ChatMain import ChatMain
from Premium import Premium
from User import User

#TODO: It doesn't work always, after executing some times the threads don't stop correctly, locking the code
class App:
    def __init__(self):
        self.NPREMIUM = 2
        self.NUSERS = 4

        self.chatMain = ChatMain()
        
        self.premium = []
        self.users = []

    def create_threads(self):
        self.nutriChat = NutriChat(self.chatMain)
        for i in range(self.NPREMIUM):
            self.premium.append(Premium(self.chatMain))
            
        for i in range(self.NUSERS):
            self.users.append(User(self.chatMain))

    def start_threads(self):
        self.nutriChat.start()
        for user in self.users:
            user.start()
        for premium in self.premium:
            premium.start()

    def interrupt_threads(self):
        for user in self.users:
            user.interrupt()
        for premium in self.premium:
            premium.interrupt()
        self.nutriChat.stop()

    def wait_end_of_threads(self):
        for user in self.users:
            user.join()
            if user.is_alive():
                print('USER STILL ALIVE')

        for premium in self.premium:
            premium.join()
            if premium.is_alive():
                print('PREMIUM STILL ALIVE')
        
        self.nutriChat.join(timeout=5)
        if self.nutriChat.is_alive():
            print('CHAT ALIVE')

    def run(self):
        self.create_threads()
        self.start_threads()

        try:
            time.sleep(5)  # Let threads run for a while
        except Exception as e:
            print("Main thread interrupted.")

        self.interrupt_threads()
        self.wait_end_of_threads()

if __name__ == "__main__":
    app = App()
    app.run()
