from src.com.stock.common.import_lib import *


if __name__ == "__main__":

    test_date = get_kr_working_day("20201111", "20201113")
    input_dict = {
        "first_gubun": "",
        "first_trans_price": "",
        "2nd_foreign": "",
        "2nd_program": "",
        "2nd_company": "",
        "2nd_trans_price": "",
        "2nd_gubun": "",
        "2nd_up_target": ""
    }
    input_dict["first_gubun"] = "5"
    input_dict["first_trans_price"] = "10000000000"
    input_dict["2nd_foreign"] = "0"
    input_dict["2nd_program"] = "0"
    input_dict["2nd_company"] = "0"
    input_dict["2nd_trans_price"] = "13000000000"
    input_dict["2nd_gubun"] = "2"
    input_dict["2nd_up_target"] = 3.0

    expected_stock_code_list = {}
    for i in test_date:
        first_condition_data = first_condition(i , input_dict)
        expected_stock_code_list[i.strftime("%Y%m%d")] = copy(first_condition_data)

    checked_result_data = check_with_data(expected_stock_code_list , 5.0)
    actual_data = check_actual_total_data(test_date, 5.0)

    for i in test_date:
        print(i.strftime("%Y%m%d")+ "일자 상승 예상 종목  수  " + str(checked_result_data[i.strftime("%Y%m%d")+"_total_count"]))
        print(i.strftime("%Y%m%d") + "일자 상승 예측 성공 종목수   " +str( checked_result_data[i.strftime("%Y%m%d")+"_success"]))
        print(i.strftime("%Y%m%d") + "일자 실제 상승 종목수  " + str(actual_data[i.strftime("%Y%m%d")+"_success"]))
        print(i.strftime("%Y%m%d") + "일자 상승 종목 전종목 대비 비율    " + str(int(actual_data[i.strftime("%Y%m%d")+"_success"])/int(actual_data[i.strftime("%Y%m%d")+"_total_count"])*100.0))
        print(i.strftime("%Y%m%d") + "일자 상승 종목 예측 성공률    " + str(int(checked_result_data[i.strftime("%Y%m%d")+"_success"])/int(checked_result_data[i.strftime("%Y%m%d")+"_total_count"])*100.0))
