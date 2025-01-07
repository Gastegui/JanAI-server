import threading
import time

class ChatMain:
    
    def __init__(self):
        self.chatSleep = threading.Semaphore(0)
        self.premiumTurnstile = threading.Semaphore(1)
        self.userTurnstile = threading.Semaphore(1)
        self.mutex = threading.Semaphore(1)
        self.userWait = threading.Semaphore(0)
        self.usersDone = threading.Semaphore(0)
        self.premiumWait = threading.Semaphore(0)
        self.premiumDone = threading.Semaphore(0)
        self.nPremium = 0
        self.nUsers = 0
        print(f"ChatMain instance created: {id(self)}")

    
    def beChat(self): #TODO: Add interrupted exception
        self.chatSleep.acquire()
        self.mutex.acquire()
        if self.nPremium == 2:
            self.mutex.release()

            #NutriChat.answerPremium()
            print("🎅 NutriChat is ANSWERING to Premium users")

            self.premiumWait.release()
            self.premiumDone.acquire()

            #NutriChat.finishPremium()
            print("🎅 NutriChat FINISHED with Premium users")
            print("AMOUNT OF PREMIUM: " + str(self.nPremium))
            print("AMOUNT OF USERS: " + str(self.nUsers))

        elif self.nUsers == 4:
            print("MAX NUMBER OF USERS")
            self.mutex.release()

            #NutriChat.answerUser()
            print("💈 NutriChat HELPING normal users")

            self.userWait.release()
            self.usersDone.acquire()

            #NutriChat.finishUser()
            print("💈 NutriChat STOPS helping")

            print("AMOUNT OF USERS: " + str(self.nUsers))
            print("AMOUNT OF PREMIUM: " + str(self.nPremium))
        else:
            self.mutex.release()
        
    
    def helpChat(self): #premium
        self.premiumTurnstile.acquire()

        self.mutex.acquire()
        self.nPremium = self.nPremium + 1
        if (self.nPremium == 2):
            self.chatSleep.release()
        else:
            self.premiumTurnstile.release()
        
        self.mutex.release()

        self.premiumWait.acquire()
        self.premiumWait.release()

        print('Premium is chatting with NutriChat' + '\n')
        time.sleep(1)

        self.mutex.acquire()
        self.nPremium = self.nPremium - 1
        if (self.nPremium == 0):
            self.premiumDone.release()
            self.premiumWait.acquire()
            self.premiumTurnstile.release()
        
        self.mutex.release()

    def askForHelp(self):  # user
        print("User attempting to acquire userTurnstile...")
        self.userTurnstile.acquire()
        print("User acquired userTurnstile")

        self.mutex.acquire()
        self.nUsers += 1
        print(f"User added, current nUsers: {self.nUsers}")
        if self.nUsers == 4:
            print("Threshold reached, releasing chatSleep")
            self.chatSleep.release()
        else:
            print("Releasing userTurnstile")
            self.userTurnstile.release()

        self.mutex.release()

        self.userWait.acquire()
        print("User is chatting with NutriChat")

        time.sleep(1)

        self.mutex.acquire()
        self.nUsers -= 1
        print(f"User finished, current nUsers: {self.nUsers}")
        if self.nUsers == 0:
            print("No more users waiting, releasing usersDone and userTurnstile")
            self.usersDone.release()
            self.userTurnstile.release()
        else:
            print("Releasing another user to chat")
            self.userWait.release()

        self.mutex.release()