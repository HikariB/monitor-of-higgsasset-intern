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

    @Test
    public void orZero() {
        double num = 1;
        System.out.println(2 | 20);
    }

// leetcode
//    public ListNode oddEvenList(ListNode head) {
//
//        if (head == null || head.next == null || head.next.next == null)
//            return head;
//        ListNode lhead = head;
//        ListNode lsecond = head.next;
//        ListNode odd = head;
//        ListNode even = odd.next;
//
//        while (even != null && even.next != null) {
//            //连接
//            odd.next = even.next;
//            even.next = even.next.next;
//            //移动指针
//            odd = odd.next;
//            even = even.next;
//        }
//        odd.next = lsecond;
//        return lhead;
//
//    }
//
//    class ListNode {
//        int val;
//        ListNode next;
//
//        ListNode(int x) {
//            val = x;
//        }
//    }
}
