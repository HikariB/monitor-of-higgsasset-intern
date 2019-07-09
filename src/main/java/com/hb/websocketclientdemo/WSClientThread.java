package com.hb.websocketclientdemo;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.hb.websocketclientdemo.model.Topic;
import com.hb.websocketclientdemo.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实现 InitializingBean
 * 将会在所有类加载完后
 * 容器加载完成之后进行开启一个线程进行数据更新操作
 * 参考：https://blog.csdn.net/wumanxin2018/article/details/80367948
 *
 * 还可以参考：https://blog.csdn.net/smithallenyu/article/details/81142862
 * 但没试过
 *
 */


@Component
public class WSClientThread implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(WSClientThread.class);

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public void afterPropertiesSet() throws Exception {
//        new Thread(() -> {
//            webSocketService.connect();
//            try {
//                webSocketService.login("gt_w", "higgspass");
//                Topic[] topics = new Topic[1];
//                topics[0] = new Topic("83925101", "IF1908");
//                webSocketService.subscribe(topics);
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        }).start();
        logger.info("thread is running");
    }
}
