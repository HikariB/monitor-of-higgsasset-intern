package com.hb.websocketclientdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.data.OrderRtn;

public class FastJsonDemo {

    public static void main(String[] args) {
//        String str3 = "{\"channel\":\"subscribe\", \"topics\":[{\"account\":\"83925087\", \"instrument\":\"IF1904\"}, {\"account\":\"83925087\", \"instrument\":\"IF1905\"}, {\"account\":\"83925087\", \"instrument\":\"IF1906\"}]}";
//
//        System.out.println(str3);
//
//        JSONObject jsonObject = JSONObject.parseObject(str3);
//        Object object = jsonObject.get("topics");
//        System.out.println(jsonObject.get("channel"));

        String str = "{\"channel\":\"order_rtn\",\"data\":{\"user_id\":\"83925101\",\"instrument_id\":\"IF1908\",\"order_sys_id\":\"      148188\",\"order_ref\":\"      270353\",\"direction\":\"1\",\"offset_flag\":\"1\",\"price\":\"3819.800000\",\"total_volume\":\"1\",\"traded_volume\":\"0\",\"insert_time\":\"\",\"order_status\":\"1\"}}\n";
        JSONObject jsonObject = (JSONObject) JSONObject.parseObject(str).get("data");

        String str1 = jsonObject.toJSONString();
        System.out.println(str1);

        OrderRtn orderRtn = JSONObject.parseObject(str1, OrderRtn.class);



    }

}
