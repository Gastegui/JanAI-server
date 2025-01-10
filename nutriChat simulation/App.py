import time
from NutriChat import NutriChat
from ChatMain import ChatMain
from Premium import Premium
from User import User

#TODO: It doesn't work always, after executing some times the threads don't stop correctly, locking the code
class App:

    NPREMIUM = 1
    NUSERS = 1

    def __init__(self):
        self.chatMain = ChatMain()
        
        self.premium = []
        self.users = []

    def create_threads(self):
        self.nutriChat = NutriChat(self.chatMain)

        self.Premium = Premium(self.chatMain)
        self.User = User(self.chatMain)

        """for i in range(self.NPREMIUM):
            self.premium.append(Premium(self.chatMain))
        for i in range(self.NUSERS):
            self.users.append(User(self.chatMain))"""

    def start_threads(self):
        self.nutriChat.start()
        self.Premium.start()
        self.User.start()
        """for user in self.users:
            user.start()
        for premium in self.premium:
            premium.start()"""

    def interrupt_threads(self):
        self.User.interrupt()
        self.Premium.interrupt()
        """for user in self.users:
            user.interrupt()
        for premium in self.premium:
            premium.interrupt()"""
        self.nutriChat.stop()

    def wait_end_of_threads(self):
        self.Premium.join(timeout=5)
        if self.Premium.is_alive():
            print("PREMIUM STILL ALIVE")

        self.User.join(timeout=5)
        if self.User.is_alive():
            print("USER STILL ALIVE")

        """for user in self.users:
            user.join()

        for premium in self.premium:
            premium.join()"""
        
        self.nutriChat.join(timeout=5)
        if self.nutriChat.is_alive():
            print("NUTRICHAT STILL ALIVE")

    def run(self):
        self.create_threads()
        self.start_threads()

        try:
            time.sleep(10)  # Let threads run for a while
        except Exception as e:
            print("Main thread interrupted.")

        self.interrupt_threads()
        self.wait_end_of_threads()

if __name__ == "__main__":
    app = App()
    app.run()
