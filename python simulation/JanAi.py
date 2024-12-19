import threading

class JanAi:
    def __init__(self):
        self.n_users = 0
        self.MAX_USERS = 10
        self.maintenance_on = False

        self.mutex = threading.Semaphore(1)
        self.server_access = threading.Semaphore(self.MAX_USERS)

    def login_user(self):
        # Adquirir permiso para acceder al servidor
        self.server_access.acquire()
        self.mutex.acquire()
        try:
            if self.maintenance_on:
                self.server_access.release()
                raise Exception("Ongoing maintenance, can't login")
            self.n_users += 1
        finally:
            self.mutex.release()

    def logoff_user(self):
        try:
            self.mutex.acquire()
            self.n_users -= 1
        finally:
            self.mutex.release()
            self.server_access.release()

    def undergo_maintenance(self):
        self.mutex.acquire()
        try:
            self.maintenance_on = True
            print("Starting maintenance, kicking users out.")
            self.n_users = 0
        finally:
            self.mutex.release()
            # Vaciar los permisos disponibles del servidor
            while self.server_access.acquire(blocking=False):
                pass

    def finish_maintenance(self):
        try:
            self.mutex.acquire()
            self.maintenance_on = False
            print("Maintenance finished.")
            # Restaurar la capacidad máxima de usuarios
            for _ in range(self.MAX_USERS):
                self.server_access.release()
        finally:
            self.mutex.release()

    