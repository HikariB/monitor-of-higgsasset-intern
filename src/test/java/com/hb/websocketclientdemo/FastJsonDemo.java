package com.hb.websocketclientdemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class FastJsonDemo {

    public static void main(String[] args) {

        String strTrim = "   a";
        System.out.println(strTrim.trim());



        String str3 = "{\"channel\":\"subscribe\", \"topics\":[{\"account\":\"83925087\", \"instrument\":\"IF1904\"}, {\"account\":\"83925087\", \"instrument\":\"IF1905\"}, {\"account\":\"83925087\", \"instrument\":\"IF1906\"}]}";
//
//        System.out.println(str3);
//
//        JSONObject jsonObject = JSONObject.parseObject(str3);
//        Object object = jsonObject.get("topics");
//        System.out.println(jsonObject.get("channel"));

//        String str = "{\"channel\":\"order_rtn\",\"data\":{\"user_id\":\"83925101\",\"instrument_id\":\"IF1908\",\"order_sys_id\":\"      148188\",\"order_ref\":\"      270353\",\"direction\":\"1\",\"offset_flag\":\"1\",\"price\":\"3819.800000\",\"total_volume\":\"1\",\"traded_volume\":\"0\",\"insert_time\":\"\",\"order_status\":\"1\"}}\n";
//        JSONObject jsonObject = (JSONObject) JSONObject.parseObject(str).get("data");
//
//        String str1 = jsonObject.toJSONString();
//        System.out.println(str1);
//
//        OrderRtn orderRtn = JSONObject.parseObject(str1, OrderRtn.class);

        String str2 = "{\"channel\":\"sub_result\",\"result\":\"success\",\"topic\":{\"account\":\"83925101\", \"instrument\":\"IF1908\"}}";
        System.out.println(JSONObject.parseObject(str3).get("topics") instanceof JSONArray);

//        在为初始化bean的 List属性时，即为null，不能使用其方法
//        SubResult subResult = new SubResult();
//        subResult.setResult("success");
//        Topic topic = new Topic("122","122");
//        List<Topic> topics = new LinkedList<>();
//        topics.add(topic);
//        subResult.setTopics(topics);
//        subResult.getTopics().remove(0);
//        System.out.println(subResult.getTopics().size());





    }

}
