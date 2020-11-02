from src.com.stock.common.import_lib import *




def make_dict(array):
    dict = {}
    for i in range(len(array)):
        dict[i] = array[i]
    return dict

def make_dict(array):
    dict = {}
    for i in range(len(array)):
        dict[i] = array[i]
    return dict

class tr_result():
    def __init__(self, list, listLen):
        self.list = list
        self.listLen = listLen

        self.result = {"list": self.list, "lsitLen": self.listLen}

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
        self.input_index = 0

        self.list = []
        self.listLen = 0

        self.input_dict_list = []
        self.pk_dict_list = []
        self.collection_len = 0

    def set_single_call(self, input_dict_list, output_dict, pk_dict_list, collection_len):
        self.list = []
        self.listLen = 0
        self.input_index = 0

        self.input_dict_list = input_dict_list
        self.pk_dict_list = pk_dict_list
        self.collection_len = collection_len

        ret = self.IndiTR.dynamicCall("SetQueryName(QString)", self.tr_name)
        for key, value in input_dict_list[0].items():
            ret = self.IndiTR.dynamicCall("SetSingleData(int, QString)", key, value)
        rqid = self.IndiTR.dynamicCall("RequestData()")

        self.rqidD[rqid] = self.tr_name
        self.col_name = output_dict

    def single_call(self):
        ret = self.IndiTR.dynamicCall("SetQueryName(QString)", self.tr_name)
        for key, value in self.input_dict_list[self.input_index].items():
            ret = self.IndiTR.dynamicCall("SetSingleData(int, QString)", key, value)
        rqid = self.IndiTR.dynamicCall("RequestData()")
        self.rqidD[rqid] = self.tr_name

    def ReceiveData(self, rqid):
        TRName = self.rqidD[rqid]
        if TRName == self.tr_name:
            nCnt = self.IndiTR.dynamicCall("GetMultiRowCount()")

            self.list = []
            self.listLen = nCnt

            for i in range(0, nCnt):
                # 데이터 받기
                DATA = {}
                for key , value in self.pk_dict_list[self.input_index].items():
                    DATA[key] = value
                for key, value in self.col_name.items():
                    DATA[value.strip()] = self.IndiTR.dynamicCall("GetMultiData(int, int)", i, key)
                    # print(DATA)
                    update_collection(self.collection, DATA)
                    self.list.append(DATA)
            print(self.list)
            print()
            self.input_index += 1
            if self.input_index != self.collection_len:
                self.single_call()
            else:
                QCoreApplication.instance().exit()

    def GetDataAll(self):
        result = tr_result(self.list, self.listLen)
        return result
    # 시스템 메시지를 받은 경우 출력합니다.
    def ReceiveSysMsg(self, MsgID):
        print("System Message Received = ", MsgID)
        print("System Error Message Received = ",self.IndiTR.GetErrorMessage())


