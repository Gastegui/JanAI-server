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

    
    def beChat(self): #TODO: Add interrupted exception
        self.chatSleep.acquire()
        self.mutex.acquire()
        if (self.nPremium == 3):
            self.mutex.release()

            #NutriChat.answerPremium()
            print("🎅 NutriChat is ANSWERING to Premium users")

            self.premiumWait.release()
            self.premiumDone.acquire()

            #NutriChat.finishPremium()
            print("🎅 NutriChat FINISHED with Premium users")

        elif (self.nUsers == 5):
            self.mutex.release()

            #NutriChat.answerUser()
            print("💈 NutriChat HELPING normal users")

            self.userWait.release()
            self.usersDone.acquire()

            #NutriChat.finishUser()
            print("💈 NutriChat STOPS helping")
        else:
            self.mutex.release()
        
    
    def helpChat(self): #premium
        self.premiumTurnstile.acquire()

        self.mutex.acquire()
        self.nPremium = self.nPremium + 1
        if (self.nPremium == 3):
            self.chatSleep.release()
        else:
            self.premiumTurnstile.release()
        
        self.mutex.release()

        self.premiumWait.acquire()
        self.premiumWait.release()

        
        print('Premium is chatting with NutriChat' + '\n')
        time.sleep(1)
        #time.sleep(random.random(0, 3/100) + 1)

        self.mutex.acquire()
        self.nPremium = self.nPremium - 1
        if (self.nPremium == 0):
            self.premiumDone.release()
            self.premiumWait.acquire()
            self.premiumTurnstile.release()
        
        self.mutex.release()

    def askForHelp(self): #user
        self.userTurnstile.acquire()
        self.mutex.acquire()
        self.nUsers = self.nUsers + 1
        if (self.nUsers == 5):
            self.chatSleep.release()
        else:
            self.userTurnstile.release()
        
        self.mutex.release()

        self.userWait.acquire()

        print('User is chatting with NutriChat' + '\n')
        time.sleep(1)

        self.mutex.acquire()
        self.nUsers = self.nUsers - 1
        if (self.nUsers == 0):
            self.usersDone.release()
            self.userTurnstile.release()
        else:
            self.userWait.release()
        
        self.mutex.release()