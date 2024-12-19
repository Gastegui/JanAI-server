import threading
import time

from NormalUser import NormalUser
from PremiumUser import PremiumUser
from Admin import Admin
from JanAi import JanAi

class Main:
    def __init__(self):
        self.NUSERS = 20
        self.NPREMIUM = 5
        self.NADMINS = 1

        self.janai = JanAi()
        self.users = []
        self.premiums = []
        self.admin = None

    def initialize(self):
        for i in range(self.NUSERS):
            self.users.append(NormalUser(self.janai, i))
        for i in range(self.NPREMIUM):
            self.premiums.append(PremiumUser(self.janai, i))
        self.admin = Admin(self.janai, 1)

    def start_threads(self):
        for user in self.users:
            user.start()
        for premium in self.premiums:
            premium.start()
        self.admin.start()

    def interrupt_threads(self):
        for user in self.users:
            user.interrupt()
        for premium in self.premiums:
            premium.interrupt()
        self.admin.interrupt()

    def wait_end_of_threads(self):
        for user in self.users:
            user.join()
        for premium in self.premiums:
            premium.join()
        self.admin.join()

    def main(self):
        self.initialize()
        self.start_threads()

        try:
            time.sleep(20)  # Simular tiempo de ejecución
        except KeyboardInterrupt:
            pass
        self.interrupt_threads()
        self.wait_end_of_threads()

if __name__ == "__main__":
    app = Main()
    app.main()
