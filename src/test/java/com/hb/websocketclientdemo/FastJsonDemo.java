package com.hb.websocketclientdemo;

import com.alibaba.fastjson.JSONObject;
import com.hb.websocketclientdemo.model.data.jsonData.DepthMarketDataDO;
import com.hb.websocketclientdemo.model.data.jsonData.TradeRtnDO;

public class FastJsonDemo {

    public static void main(String[] args) {





//        String str3 = "{\"channel\":\"subscribe\", \"topics\":[{\"account\":\"83925087\", \"instrument\":\"IF1904\"}, {\"account\":\"83925087\", \"instrument\":\"IF1905\"}, {\"account\":\"83925087\", \"instrument\":\"IF1906\"}]}";
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

//        String str2 = "{\"channel\":\"sub_result\",\"result\":\"success\",\"topic\":{\"account\":\"83925101\", \"instrument\":\"IF1908\"}}";
//        System.out.println(JSONObject.parseObject(str3).get("topics") instanceof JSONArray);

//        在为初始化bean的 List属性时，即为null，不能使用其方法
//        SubResult subResult = new SubResult();
//        subResult.setResult("success");
//        Topic topic = new Topic("122","122");
//        List<Topic> topics = new LinkedList<>();
//        topics.add(topic);
//        subResult.setTopics(topics);
//        subResult.getTopics().remove(0);
//        System.out.println(subResult.getTopics().size());\
//        String info = "{\"channel\":\"instrument_info\",\"data\":{\"instrument_id\":\"IF1908\",\"exchange_id\":\"CFFEX\",\"expire_date\":\"20190816\",\"contract_multiplier\":\"300\",\"price_tick\":\"0.200000\",\"strike_price\":\"0.000000\",\"pre_close_price\":\"3761.800000\",\"pre_settlement_price\":\"3757.800000\",\"upper_limit_price\":\"4133.400000\",\"lower_limit_price\":\"3382.200000\"}}";
//
//        JSONObject jsobj = JSONObject.parseObject(info);
//        InstrumentInfoDO instrumentInfo = JSONObject.parseObject(((JSONObject)jsobj.get("data")).toJSONString(),InstrumentInfoDO.class);
//
//        //带空格的String 无法转换为 int
//        String order = "{\"user_id\":\"83925101\",\"instrument_id\":\"IF1908\",\"order_sys_id\":\"  148188\",\"order_ref\":\"   270353\",\"direction\":\"1\",\"offset_flag\":\"1\",\"price\":\"3819.800000\",\"total_volume\":\"1\",\"traded_volume\":\"0\",\"insert_time\":\"\",\"order_status\":\"1\"}".trim();
//        OrderRtnDO orderRtn = JSONObject.parseObject(order.trim(),OrderRtnDO.class);

        String trade = "{\"user_id\":\"83925101\",\"instrument_id\":\"IF1908\",\"order_sys_id\":\"      148291\",\"order_ref\":\"      270363\",\"trade_id\":\"       30376\",\"direction\":\"1\",\"offset_flag\":\"1\",\"price\":\"3819.800000\",\"volume\":\"1\",\"fee\":\"0.000000\"}";
        TradeRtnDO tradeRtnDO = JSONObject.parseObject(trade,TradeRtnDO.class);

        String market = "{\"instrument_id\":\"IF1908\",\"trading_date\":\"20190709\",\"update_time\":\"13:35:28\",\"update_millisec\":\"100\",\"last_price\":\"3757.000000\",\"adjusted_price\":\"3757.000000\",\"volume\":\"1109\",\"turnover\":\"1253183520.000000\",\"bids\":[[\"3756.000000\",\"3\"],[\"0.000000\",\"0\"],[\"0.000000\",\"0\"],[\"0.000000\",\"0\"],[\"0.000000\",\"0\"]],\"asks\":[[\"3760.600000\",\"1\"],[\"0.000000\",\"0\"],[\"0.000000\",\"0\"],[\"0.000000\",\"0\"],[\"0.000000\",\"0\"]]}";

        DepthMarketDataDO marketDataDO = JSONObject.parseObject(market,DepthMarketDataDO.class);


    }

}
