package com.zongcc.LRU;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chunchengzong
 * @date 2019-03-21 15:26
 **/
public class LruTest {
    class Node {
        Node pre;
        Node next;
        String key;
        String value;

        public Node(String k, String v) {
            this.key = k;
            this.value = v;
        }
    }

    Map<String, Node> dataMap;

    {
        dataMap = new HashMap<String, Node>();
    }

    Node head;
    Node tail;
    int cap;

    public LruTest(int cap) {
        this.cap = cap;
        this.head = new Node(null, null);
        this.tail = new Node(null, null);
        head.next = tail;
        tail.pre = head;
    }

    public void appendToTail(Node node) {
        node.next = tail;
        node.pre = tail.pre;
        tail.pre.next = node;
        tail.pre = node;
    }

    public String getValue(String key) {
        Node node = dataMap.get(key);
        if (null != node) {
            node.next.pre = node.pre;
            node.pre.next = node.next;
            appendToTail(node);
            return node.value;
        }
        return "-1";
    }

    public void setValue(String key, String value) {
        Node s = dataMap.get(key);
        //存在
        if (null != s) {
            s.value = value;
            dataMap.put(key, s);
            s.next.pre = s.pre;
            s.pre.next = s.next;
            appendToTail(s);
        }
        //扩容
        if (dataMap.size() == cap) {
            Node tmp = head.next;
            head.next.pre = head;
            head.next = head.next.next;
            dataMap.remove(tmp.key);
        }
        //正常
        Node node = new Node(key, value);
        appendToTail(node);
        dataMap.put(key, node);
    }

    public static void main(String[] args) {
        LruTest lru = new LruTest(5);
        lru.setValue("0","10");
        lru.setValue("1","11");
        lru.setValue("2","12");
        lru.setValue("3","13");
        lru.setValue("4","14");
        lru.setValue("5","15");
        for (int i=0;i<7;i++){
            System.out.println(lru.getValue(i+""));
        }
    }

}