package com.zongcc.thread;

import org.apache.commons.lang.ArrayUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by chunchengzong on 2017-03-02.
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) throws Exception {

        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("b");
        a.add("c");
        final CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>(a);
        Thread t = new Thread(new Runnable() {
            int count = -1;

            @Override
            public void run() {
                while (true) {
                    if(count<100) {
                        list.add(count++ + "");
                    }
                }
            }
        });
        t.setDaemon(true);
        t.start();
        Thread.currentThread().sleep(3);
        for (String s : list) {
            System.out.println(list.hashCode());
            System.out.println(s);
        }

        //1、list to int[]
        List<Integer> nList = new ArrayList<Integer>();
        Integer[] integerList = nList.toArray(new Integer[0]);//int[] intList = nList.toArray();无法实现只能得到integer
        int[] intList = ArrayUtils.toPrimitive(nList.toArray(new Integer[0]));
        //2、int[] to list
        int[] ints = {7,0,2,3,4,6,7,5,1};
        //List<Integer> lists = Arrays.asList(ints);//该方法对于基本数据类型的数组支持并不好
        List<Integer> lists = new ArrayList<Integer>();
        for(int i: ints) {
            lists.add(i);
        }
        System.out.println("=======================================================");
        //3、如过滤掉list中大于5的整数。
        Iterator<Integer> iterator = lists.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        System.out.println("=======================================================");
        //4、将List转化成Set最简单的方法
        Set<Integer> set = new HashSet<Integer>(lists);
        Iterator<Integer> iterator1 = set.iterator();
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
        //4.1、remove列表中所有重复
       /* lists.clear();
        lists.addAll(set);*/

        System.out.println("=======================================================");
        Set<Integer> treeSet = new TreeSet<Integer>();
        treeSet.addAll(lists);
        Iterator<Integer> iterator2 = treeSet.iterator();
        while (iterator2.hasNext()){
            System.out.println(iterator2.next());
        }

        System.out.println("=======================================================");
        //5、拷贝list
        ArrayList<Integer> copyList = new ArrayList<Integer>(lists);
        Iterator<Integer> iterator3 = copyList.iterator();
        while (iterator3.hasNext()){
            System.out.println("iterator3--"+iterator3.next());
        }

        System.out.println("=======================================================");
        //虽然这是size只有在add或者remove操作才有size变化，copy操作时没有默认为0，报IndexOutOfBoundsException异常
        //ArrayList<Integer> dstList  = new ArrayList<Integer>(lists.size());
        ArrayList<Integer> dstList  = new ArrayList<Integer>(Arrays.asList(new Integer[lists.size()]));//最后设置长度，可能IndexOutOfBoundsException
        Collections.copy(dstList ,lists);
        Iterator<Integer> iterator4 = dstList.iterator();
        while (iterator4.hasNext()){
            System.out.println("iterator4--"+iterator4.next());
        }

        System.out.println("=======================================================");

        //ExecutorService executorService = Executors.newFixedThreadPool(5);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,200,TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(7));
        //当线程池中线程的数目大于5时，便将任务放入任务缓存队列里面，当任务缓存队列满了之后，便创建新的线程直到最大size
        // 如果上面程序中，将for循环中改成执行（10+7<）18个任务，就会抛出任务拒绝异常了。
        for (int i=0;i<17;i++){
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+" 开始");
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" 结束");
                }
            });
            System.out.println("线程池中线程数目："+executor.getPoolSize()
                    +"，队列中等待执行的任务数目："+executor.getQueue().size()
                    +"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();

    }
}
