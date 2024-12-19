import threading
import random
from UserMacros import UserMacros
import time

class NormalUser(threading.Thread):
    def __init__(self, janai, i):
        super().__init__(name=f"User {i}")
        self.janai = janai
        self.rand = random.Random()
        self.action = 0
        self.meals_registered = 0
        self.macros = UserMacros(
            recommended_calories=self.rand.randint(1800, 2600),
            recommended_carbs=self.rand.randint(235, 335),
            recommended_fats=self.rand.randint(50, 83),
            recommended_fiber=self.rand.randint(28, 42),
            recommended_proteins=self.rand.randint(60, 180)
        )

    def run(self):
        while True:
            try:
                time.sleep(self.rand.randint(0, 2000) / 1000)  # Not using the app
                # Login and start using it, randomly choose what to do
                self.janai.login_user()
                self.login()
                time.sleep(self.rand.randint(0, 1000) / 1000)

                if self.meals_registered < 3:
                    self.update_progress()
                    self.check_profile()
                    self.meals_registered += 1
                    time.sleep(0.5)

                self.janai.logoff_user()
                self.logoff()
            except Exception as e:
                print(f"{self.name}: Exception encountered: {str(e)}")
                break

    def login(self):
        print(f"{self.name} has successfully logged in!!!")

    def logoff(self):
        print(f"{self.name} has logged off :(")

    def check_profile(self):
        print(f"{self.name} is checking its daily progress:")
        print(f"------------------- {self.name} ----------------------")
        print(self.macros)
        print("--------------------------------------------------")

    def update_progress(self):
        print(f"{self.name} is updating its progress.")
        self.macros.consumed_calories += self.rand.randint(600, 900)
        self.macros.consumed_carbs += self.rand.randint(52, 68)
        self.macros.consumed_fats += self.rand.randint(17, 28)
        self.macros.consumed_fiber += self.rand.randint(8, 16)
        self.macros.consumed_proteins += self.rand.randint(15, 70)