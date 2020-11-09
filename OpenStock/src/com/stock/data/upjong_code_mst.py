# -*- coding: utf-8 -*-
from src.com.stock.common.import_lib import *
from src.com.stock.common import import_lib  as com_vari



input_dict = {} # tr 호출 인풋 값 dictionary , make_dict 에 배열을 넣어 만들어준다.
col_name_in = [
    "단축코드   " ,
    "종목명     "
]  # 아웃풋 컬럼명 배열
col_name = make_dict(col_name_in) # 아웃풋 컬럼명 배열을 index ,value 형태의 dictionary로 변형
pk_dict = {"업종코드": "" , "데이터구분":""} # document 간 pk나 중요정보로 추가되어야하는 값
last_call = False

if __name__ == "__main__":
    drop_collection("stock_data", "TR_1205")
    app = QApplication(sys.argv)

    collection = make_collection("stock_data", "TR_1205")
    activate_Tr = tr_object("TR_1205", collection)


    upjong = ["0", "1"]

    start_date = get_kr_working_day("20201012", "20201109")

    data_kind = "price"

    pk_dict_list = []
    input_dict_list = []

    for i in upjong:
        for j in start_date:
            input_dict = make_dict([i, j.strftime("%Y%m%d"),j.strftime("%Y%m%d"), "K"])

            pk_dict["일자"] = j.strftime("%Y%m%d")
            pk_dict["데이터구분"] = data_kind

            input_dict_list.append(copy(input_dict))
            pk_dict_list.append(copy(pk_dict))

    collection_len = len(input_dict_list)
    activate_Tr.set_multi_call(input_dict_list, col_name, pk_dict_list, collection_len)

    app.exec_()

    total_TR_1205_count = make_collection("stock_data", "TR_1205").count()

    print("업종 분류 종류     " + str(len(upjong)))
    print("데이터 적재 기간   "+ str(len(start_date)))
    print("업종 종류 약  70 ")
    print("실제 db 적제된 document 갯수     "+str(total_TR_1205_count))

    print("원래")
