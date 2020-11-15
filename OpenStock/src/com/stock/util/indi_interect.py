from src.com.stock.common.import_lib import *
from src.com.stock.common import import_lib  as com_vari



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

        self.multi= False
        self.input_dict_list = []
        self.pk_dict_list = []
        self.collection_len = 0

    def set_single_call(self, input_dict, output_dict, pk_dict):
        self.multi= False
        ret = self.IndiTR.dynamicCall("SetQueryName(QString)", self.tr_name)
        for key, value in input_dict.items():
            ret = self.IndiTR.dynamicCall("SetSingleData(int, QString)", key, value)
        rqid = self.IndiTR.dynamicCall("RequestData()")
        self.rqidD[rqid] = self.tr_name
        self.col_name = output_dict
        self.pk_dict = pk_dict

    def set_multi_call(self, input_dict_list, output_dict, pk_dict_list, collection_len):
        self.multi= True
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

    def inner_call(self):
        ret = self.IndiTR.dynamicCall("SetQueryName(QString)", self.tr_name)
        for key, value in self.input_dict_list[self.input_index].items():
            ret = self.IndiTR.dynamicCall("SetSingleData(int, QString)", key, value)
        rqid = self.IndiTR.dynamicCall("RequestData()")
        self.rqidD[rqid] = self.tr_name

    def ReceiveData(self, rqid):
        TRName = self.rqidD[rqid]
        #com_vari.TR_1206_logger.debug("TR_1206 데이터 수신")

        if TRName == self.tr_name:
            nCnt = self.IndiTR.dynamicCall("GetMultiRowCount()")

            self.list = []
            self.listLen = nCnt
            if(self.multi):
                for i in range(0, nCnt):
                    # 데이터 받기
                    DATA = {}
                    for key , value in self.pk_dict_list[self.input_index].items():
                        DATA[key] = value
                    for key, value in self.col_name.items():
                        if self.tr_name == "TR_1205":
                            if key != 0 and key != 1 and key != 2 :
                                DATA[value.strip()] = int(self.IndiTR.dynamicCall("GetMultiData(int, int)", i, key))
                            else:
                                DATA[value.strip()] = self.IndiTR.dynamicCall("GetMultiData(int, int)", i, key)
                        else:
                            DATA[value.strip()] = self.IndiTR.dynamicCall("GetMultiData(int, int)", i, key)
                        # print(DATA)
                    update_collection(self.collection, DATA)
                    self.list.append(DATA)

                print(self.list)
                if(self.list == []):
                    for key, value in self.input_dict_list[self.input_index].items():
                        com_vari.upjong_code_mst_logger.debug("데이터없음  key  "  +  str(key)  + "  value   " + value)
                    for key , value in self.pk_dict_list[self.input_index].items():
                        com_vari.upjong_code_mst_logger.debug("데이터없음  key  "  +  key  + "  value   " + value)
                    com_vari.upjong_code_mst_logger.debug("데이터 없음 체크 완료")

                if(TRName == "TR_1206" and com_vari.TR_1206_len_counts != self.listLen):
                    logging_string = TRName +" 단축코드 : "+self.pk_dict_list[self.input_index]["단축코드"] + "가 " +str(nCnt)  + " 만큼 적재 되었습니다."
                    com_vari.TR_1206_logger.debug(logging_string)
                print()
                self.input_index += 1
                if self.input_index != self.collection_len:
                    self.inner_call()
                else:
                    QCoreApplication.instance().exit()
            else:
                for i in range(0, nCnt):
                    # 데이터 받기
                    DATA = {}
                    for key, value in self.pk_dict.items():
                        DATA[key] = value
                    for key, value in self.col_name.items():
                        DATA[value.strip()] = self.IndiTR.dynamicCall("GetMultiData(int, int)", i, key)
                    print(DATA)
                    update_collection(self.collection, DATA)
                QCoreApplication.instance().exit()

    def GetDataAll(self):
        result = tr_result(self.list, self.listLen)
        return result
    # 시스템 메시지를 받은 경우 출력합니다.
    def ReceiveSysMsg(self, MsgID):
        print("System Message Received = ", MsgID)
        print("System Error Message Received = ",self.IndiTR.GetErrorMessage())


