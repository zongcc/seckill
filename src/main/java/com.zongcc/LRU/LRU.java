package com.zongcc.LRU;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chunchengzong on 2017-07-04.
 */
public class LRU {
    class Node{
        String key;
        String value;
        Node pre;
        Node next;
        public Node(String key,String value){
            this.key = key;
            this.value = value;
        }
    }
    Node head = new Node(null,null);//头结点(最新数据)
    Node tail = new Node(null,null);//尾结点(最老数据)
    int size;
    public LRU(int size){
        this.size = size;
        head.next = tail;
        tail.pre = head;
    }
    Map<String,Node> map = new HashMap<String,Node>();

    public String get(String key){
        Node node =  map.get(key);
        if(null != node){
            //调换中间位置指向
            node.pre.next=node.next;
            node.next.pre=node.pre;
            //调整到头部
            appendToHead(node);
            return map.get(key).value;
        }
        return "-1";
    }

    public Node set(String key,String value){
        Node node =  map.get(key);
        //结点存在，调整顺序
        if(null != node){
            node.value = value;
            map.put(key,node);
            //调换中间位置指向
            node.pre.next=node.next;
            node.next.pre=node.pre;
            appendToHead(node);
            return node;
        }
        //判断长度
        if(map.size() == size){
            Node temp = tail.pre;
            tail.pre = tail.pre.pre;
            tail.pre.next = tail;
            map.remove(temp.key);
        }
        //success
        Node n = new Node(key,value);
        appendToHead(n);
        map.put(key,n);
        return n;
    }

    public void appendToHead(Node n) {
        n.next = head.next;
        n.pre = head;
        head.next.pre = n;
        head.next = n;
    }


}
