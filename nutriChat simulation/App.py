import time
from NutriChat import NutriChat
import ChatMain
from Premium import Premium
from User import User

#TODO: It doesn't work always, after executing some times the threads don't stop correctly, locking the code
class App:
    NPREMIUM = 3
    NUSERS = 5

    def __init__(self):
        self.chatMain = ChatMain.ChatMain()
        self.nutriChat = NutriChat(self.chatMain)
        self.premium = [None] * self.NPREMIUM
        self.users = [None] * self.NUSERS

    def create_threads(self):
        for i in range(self.NPREMIUM):
            self.premium[i] = Premium(self.chatMain)
        for i in range(self.NUSERS):
            self.users[i] = User(self.chatMain)

    def start_threads(self):
        self.nutriChat.start()
        for premium_user in self.premium:
            premium_user.start()
        for user in self.users:
            user.start()

    def interrupt_threads(self):
        for premium_user in self.premium:
            premium_user.stop()
        for user in self.users:
            user.stop()
        self.nutriChat.stop()

    def wait_end_of_threads(self):
        for premium_user in self.premium:
            premium_user.join(timeout=5)
            """
            if not premium_user.is_alive():
                print('PREMIUM NOT ALIVE')
            """
        
        for user in self.users:
            user.join(timeout=5)
            """
            if user.is_alive():
                print('USER STILL ALIVE')
            """
        
        self.nutriChat.join(timeout=5)
        """
        if self.nutriChat.is_alive():
            print('CHAT ALIVE')
        """

    def run(self):
        self.create_threads()
        self.start_threads()

        try:
            time.sleep(20)  # Let threads run for a while
        except Exception as e:
            print("Main thread interrupted.")

        self.interrupt_threads()
        self.wait_end_of_threads()

if __name__ == "__main__":
    app = App()
    app.run()
