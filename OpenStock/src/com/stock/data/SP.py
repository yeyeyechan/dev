# -*- coding: utf-8 -*-
from src.com.stock.common.import_lib import *
from src.com.stock.common import import_lib  as com_vari



input_dict = {} # tr 호출 인풋 값 dictionary , make_dict 에 배열을 넣어 만들어준다.
col_name_in = ["표준코드              ",
"단축코드              ",
"일자                  ",
"시간                  ",
"차익매도호가잔량      ",
"차익매수호가잔량      ",
"비차익매도호가잔량    ",
"비차익매수호가잔량    ",
"차익매도호가수량      ",
"차익매수호가수량      ",
"비차익매도호가수량    ",
"비차익매수호가수량    ",
"매도위탁체결수량      ",
"매도자기체결수량      ",
"매수위탁체결수량      ",
"매수자기체결수량      ",
"매도위탁체결금액      ",
"매도자기체결금액      ",
"매수위탁체결금액      ",
"매수자기체결금액      ",
"매도사전공시수량      ",
"매수사전공시수량      ",
"매도공시사전정정수량  ",
"매수공시사전정정수량  ",
"사후공시매도수량      ",
"사후공시매수수량      ",
"차익매도위탁체결수량  ",
"차익매도자기체결수량  ",
"차익매수위탁체결수량  ",
"차익매수자기체결수량  ",
"비차익매도위탁체결수량",
"비차익매도자기체결수량",
"비차익매수위탁체결수량",
"비차익매수자기체결수량",
"차익매도위탁체결금액  ",
"차익매도자기체결금액  ",
"차익매수위탁체결금액  ",
"차익매수자기체결금액  ",
"비차익매도위탁체결금액",
]  # 아웃풋 컬럼명 배열
col_name = make_dict(col_name_in) # 아웃풋 컬럼명 배열을 index ,value 형태의 dictionary로 변형
pk_dict = {"": ""} # document 간 pk나 중요정보로 추가되어야하는 값

if __name__ == "__main__":
    app = QApplication(sys.argv)

    logic_name = "logic3"


    from_collection = make_collection("stock_data", logic_name)

    collection = make_collection("stock_data", "Real_Time_SP")
    activate_Tr = real_tr_object("SP", collection)

    input_list = []
    for i in from_collection.find({"일자": com_vari.Today_date}):
        input_list = copy(i["stock_code"])

    collection_len = len(input_list)
    activate_Tr.set_multi_call(input_list, col_name, pk_dict, collection_len)

    app.exec_()


