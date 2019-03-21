package com.zongcc.binaryTree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by chunchengzong on 2017-02-21.
 */
public class BinaryTree {
    protected Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    /** 构造树 */
    public static Node init() {
        Node a = new Node('A');
        Node b = new Node('B', null, a);
        Node c = new Node('C');
        Node d = new Node('D', b, c);
        Node e = new Node('E');
        Node f = new Node('F', e, null);
        Node g = new Node('G', null, f);
        Node h = new Node('H', d, g);
        return h;// root
    }

    /** 访问节点 */
    public static void visit(Node p) {
        System.out.print(p.getKey() +"");
    }

    /** 递归实现前序遍历 */
    static void preorder(Node p) {
        if (p != null) {
            visit(p);
            preorder(p.getLeft());
            preorder(p.getRight());
        }
    }

    /** 递归实现中序遍历 */
    static void midorder(Node p) {
        if (p != null) {
            preorder(p.getLeft());
            visit(p);
            preorder(p.getRight());
        }
    }

    /** 递归实现后序遍历 */
    static void lastorder(Node p) {
        if (p != null) {
            preorder(p.getLeft());
            preorder(p.getRight());
            visit(p);
        }
    }



    /** 层次遍历*/
    static void levelorder(Node p){
        if(p==null) return;
        Queue<Node> queue=new LinkedList<Node>();
        queue.offer(p);
        while(queue.size()>0){
            Node temp=queue.poll();
            visit(temp);
            if(temp.getLeft()!=null){
                queue.offer(temp.getLeft());
            }
            if(temp.getRight()!=null){
                queue.offer(temp.getRight());
            }
        }

    }

//将二叉树所有结点的左右子树交换

    static void swapTree(Node root){

        if(root != null) {

            Node tmp = root.getLeft();
            root.setLeft(root.getRight());
            root.setRight(tmp);

            swapTree(root.getLeft());
            swapTree(root.getRight());

        }
    }


//输出二叉树的嵌套括号表示

    static void display(Node tree){
        if(tree!=null){
            System.out.printf("%c",tree.getKey());
            if(tree.getLeft()!=null||tree.getRight()!=null){
                System.out.printf("(");
                display(tree.getLeft());
                if(tree.getRight()!=null)
                    System.out.printf(",");
                display(tree.getRight());
                System.out.printf(")");
            }
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree(init());
        display(tree.getRoot());
        System.out.printf("\n");
        swapTree(tree.getRoot());
        display(tree.getRoot());
        // 中左右
        System.out.printf("\n前序遍历：");
        preorder(tree.getRoot());
        // 左中右
        System.out.printf("\n中序遍历：");
        midorder(tree.getRoot());
        // 左右中
        System.out.printf("\n后序遍历：");
        lastorder(tree.getRoot());
    }

}


