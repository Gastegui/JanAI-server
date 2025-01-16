import threading
from QueryBuffer import QueryBuffer
from AnswerBuffer import AnswerBuffer
import random

class Request(threading.Thread):
    def __init__(self, QBuffer, ABuffer, usID, reqID):
        super().__init__()
        self._stop_event = threading.Event()
        self.query = QBuffer
        self.answer = ABuffer
        self.end = False
        self.userID = usID #TODO: honen ordez requestID
        self.reqID = reqID
        self.reqData = []
        #Erantzunak buzoietan sartu. Beittu in bida danak aber bere erantzuna aber allau dan. Modeloak erabiltzailean erantzuna bakarrik emun biharko euen.

    def run(self):
        #while not self._stop_event.is_set() or self.end:
        try:
            self.reqData.append(self.userID)
            self.reqData.append(self.reqID)
            rand = random.randint(1, 50)
            self.reqData.append(rand)
            
            self.query.add(self.reqData)

            item = self.answer.remove(self.reqData) #TODO: Begiratu linea hau zuzenena dan (sintaxis). Ahal bada objetua izan objetua jarri
            if item != None:
                print(str(item[0]) + " RECEIVED ANSWER FROM ANSWERBUFFER: " + str(item[2]) + "\n")

        except InterruptedError as e:
            print(f"Error in REQUEST thread {self.name}: {e}")
            self.end = True
            #break

    def interrupt(self):
        self._stop_event.set()