from src.com.stock.common.import_lib import *

def total_trans_amount (price , volume, target):
    # 가격, 누적거래량
    if int(target) <= int(price)*int(volume):
        return True
    else:
        return False
def check_for_amount(volume ,target):
    # 외국인 순매수 거래량
    if int(target) <= int(volume):
        return True
    else:
        return False
def check_com_amount(volume , target):
    #기관순매수거래량
    if int(target) <= int(volume):
        return True
    else:
        return False
def check_pro_amount(volume, target):
    #프로그램순매수
    if int(target )<= int(volume):
        return True
    else:
        return False
def check_per_amount(volume , target):
    #개인순매수거래량
    if int(target) <= int(volume):
        return True
    else:
        return False
def check_up_price(price,  diff):
    before = int(price) -int(diff)
    if  int(diff)/before *100 >=3.0:
        return True
    else:
        return False

if __name__ == "__main__":
    collection = make_collection("stock_data", "TR_1206")

    stock_code_list = []
    final_stock_code_list = []
    for i in collection.find({"일자": "20201105"}):
        if i['전일대비구분'] == '5':
            if total_trans_amount(i['가격'], i['누적거래량'], '10000000000' ):
                stock_code_list.append(i['단축코드'])
    for i in stock_code_list:
        data = collection.find_one({"일자": "20201106", "단축코드":i })
        #print(data)
        if data['전일대비구분'] == '2' and check_for_amount(data['외국인순매수거래량'], '0') and check_pro_amount(data['프로그램순매수'], '0') or check_com_amount(data['기관순매수거래량'], '0') and check_up_price(data['가격'] , data['전일대비']) and total_trans_amount(data['가격'],data['누적거래량'], '13000000000'):
            final_stock_code_list.append(data['단축코드'])
    print(final_stock_code_list)