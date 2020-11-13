

# -*- coding: utf-8 -*-
from src.com.stock.common.import_lib import *
from src.com.stock.common import import_lib  as com_vari

'''
upjong_code_mst 설명


'''

input_dict = {} # tr 호출 인풋 값 dictionary , make_dict 에 배열을 넣어 만들어준다.
col_name_in = [
"단축코드",
"종목명"
]  # 아웃풋 컬럼명 배열
col_name = make_dict(col_name_in) # 아웃풋 컬럼명 배열을 index ,value 형태의 dictionary로 변형
pk_dict = {"업종코드": ""} # document 간 pk나 중요정보로 추가되어야하는 값

if __name__ == "__main__":
    drop_collection("stock_data", "upjong_code_mst")
    app = QApplication(sys.argv)

    from_collection = make_collection("stock_data", "upjong_code_mst")
    collection_len = from_collection.estimated_document_count()
    index =0
    collection = make_collection("stock_data", "TR_1206")
    activate_Tr = tr_object("TR_1206", collection)


    start_date = "20201109"
    end_date = "20201112"
    gubun = "1"
    data_kind = "0"

    check_list = integrity_db_count(start_date, end_date , collection_len)
    com_vari.TR_1206_len_counts = check_list[0]
    pk_dict["데이터구분"] = data_kind
    input_dict = make_dict(["", start_date, end_date, gubun, data_kind])

    pk_dict_list = []
    input_dict_list = []
    for i in from_collection.find():
        stock_code = i["단축코드"]

        pk_dict["단축코드"] = stock_code
        input_dict[0] = stock_code

        pk_dict_list.append(copy(pk_dict))
        input_dict_list.append(copy(input_dict))
    com_vari.TR_1206_logger.debug("TR_1206 호출 시작")
    activate_Tr.set_multi_call(input_dict_list, col_name, pk_dict_list, collection_len)
    com_vari.TR_1206_logger.debug("TR_1206 호출 완료")

    app.exec_()

    total_TR_1206_count = make_collection("stock_data", "TR_1206").estimated_document_count()

    print("예상되는 실제 db 적재 document 갯수   "+ str(check_list[1]))
    print("실제 db 적제된 document 갯수     "+str(total_TR_1206_count) )

    print("원래")
