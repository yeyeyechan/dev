from src.com.stock.common.import_lib import *

def make_indi_init(IndiTR, ReceiveData,ReceiveSysMsg ):
    IndiTR = QAxWidget("GIEXPERTCONTROL.GiExpertControlCtrl.1")
    IndiTR.ReceiveData.connect(ReceiveData)
    IndiTR.ReceiveSysMsg.connect(ReceiveSysMsg)
    return

def set_single_call(tr_class, input_dict):
    ret = tr_class.IndiTR.dynamicCall("SetQueryName(QString)", tr_class.tr_name)
    for key , value in input_dict.items():
        ret = tr_class.IndiTR.dynamicCall("SetSingleData(int, QString)", key, value)
    rqid = tr_class.IndiTR.dynamicCall("RequestData()")
    tr_class.rqidD[rqid] =  tr_class.tr_name

def make_dict(array):
    dict = {}
    for i in range(len(array)):
        dict[i] = array[i]
    return dict

class tr_object(QMainWindow):
    def __init__(self, tr_name, db_collection):
        super().__init__()

        # Indi API event
        self.IndiTR = QAxWidget("GIEXPERTCONTROL.GiExpertControlCtrl.1")
        # Indi API event
        self.IndiTR.ReceiveData.connect(self.ReceiveData)
        self.IndiTR.ReceiveSysMsg.connect(self.ReceiveSysMsg)
        self.rqidD = {}  # TR 관리를 위해 사전 변수를 하나 생성합니다.
        self.collection = db_collection
        self.tr_name = tr_name
        self.col_name = {}
        self.pk_dict = {}
        self.last_call = False

    def set_single_call(self, input_dict, output_dict, pk_dict, last_call):
        ret = self.IndiTR.dynamicCall("SetQueryName(QString)", self.tr_name)
        for key, value in input_dict.items():
            ret = self.IndiTR.dynamicCall("SetSingleData(int, QString)", key, value)
        rqid = self.IndiTR.dynamicCall("RequestData()")
        self.rqidD[rqid] = self.tr_name
        self.col_name = output_dict
        self.pk_dict = pk_dict
        self.last_call = last_call

    def ReceiveData(self, rqid):
        TRName = self.rqidD[rqid]
        if TRName == self.tr_name:
            nCnt = self.IndiTR.dynamicCall("GetMultiRowCount()")

            for i in range(0, nCnt):
                # 데이터 받기
                DATA = {}

                for key , value in self.pk_dict.items():
                    DATA[key] = value
                for key, value in self.col_name.items():
                    DATA[value.strip()] = self.IndiTR.dynamicCall("GetMultiData(int, int)", i, key)
                print(DATA)
                update_collection(self.collection, DATA)

        if self.last_call:
            QCoreApplication.instance().quit()
        return

    # 시스템 메시지를 받은 경우 출력합니다.
    def ReceiveSysMsg(self, MsgID):
        print("System Message Received = ", MsgID)