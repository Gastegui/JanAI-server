import threading
import random
import time

class Admin(threading.Thread):
    def __init__(self, janai, i):
        super().__init__(name=f"Admin {i}")
        self.janai = janai
        self.rand = random.Random()
        self.stop_event = threading.Event()

    def run(self):
        while not self.stop_event.is_set():
            try:
                time.sleep(self.rand.randint(10, 15))  # No usa la aplicación
                # Login y comienza el mantenimiento del servidor
                self.janai.login_user()
                self.login()
                time.sleep(5)
                self.janai.undergo_maintenance()
                self.start_maintenance()
                time.sleep(1)
                self.janai.finish_maintenance()
                self.end_maintenance()
                self.janai.logoff_user()
                self.logoff()

            except Exception as e:
                print(f"{self.name}: Interrupted with exception {e}")
                

    def login(self):
        print(f"{self.name} has successfully logged in!!!")


    def start_maintenance(self):
        print(f"{self.name} has started the server's maintenance.")

    def end_maintenance(self):
        print(f"{self.name} finished with the server's maintenance.")

    def logoff(self):
        print(f"{self.name} has logged off :(")


    def interrupt(self):
        self.stop_event.set()
