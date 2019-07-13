package com.hb.websocketclientdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebsocketClientDemoApplicationTests {

    @Test
    public void contextLoads() {

    }

    @Test
    public void double2() {
        double res = 123.123;
        DecimalFormat df = new DecimalFormat("#.00");
        double res2 = Double.parseDouble(df.format(res));
        System.out.println(res2);
    }

}
