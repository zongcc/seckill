package com.zongcc.staticTest;

import com.zongcc.utils.CaculateUtil;
import com.zongcc.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chunchengzong on 2017-05-09.
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    //更新map内容Integer
    public static void updateMap(Map<Integer, Long> map, Integer key, Long value) {
        Long aLong = map.get(key);
        if (null != aLong) {
            map.put(key, CaculateUtil.getLongAdd(aLong, value));
        } else {
            map.put(key, value);
        }
    }

    //try catch finally
    public static Boolean exceptionMethod() throws IOException {
        try {
            int i = 1 / 0;
            TimeUnit.SECONDS.sleep(1);
            System.out.println("run...........");
        } catch (Exception e) {
            System.out.println("exception ");
            throw new IOException();
            //return false;
        } finally {
            System.out.println("finally");
        }
        return true;
    }

    private static int exceptionMethod(int i) {
        return i + 10;
    }

    //两个列表，一个删除另一个列表（高效）
    public static List<Integer> removeElements(List<Integer> src, List<Integer> oth) {
        LinkedList result = new LinkedList(src);//大集合用linkedlist
        HashSet othHash = new HashSet(oth);//小集合用hashset
        Iterator iter = result.iterator();//采用Iterator迭代器进行数据的操作
        while (iter.hasNext()) {
            if (othHash.contains(iter.next())) {
                iter.remove();
            }
        }
        Collections.reverse(result);
        return result;
    }

    //ABA问题解决方式
    static class TestThread implements Runnable {

        private Map<Integer, AtomicLong> consumeMap;

        TestThread(Map consumeMap) {
            this.consumeMap = consumeMap;
        }

        @Override
        public void run() {
            //System.out.println(this);
            //synchronized(consumeMap){
            AtomicLong aLong = consumeMap.get(1);
            System.out.println(aLong);
            if (null != aLong) {
                consumeMap.put(1, new AtomicLong(aLong.addAndGet(1)));
            } else {
                consumeMap.put(1, new AtomicLong(0));
            }
            //}
        }
    }

    static class Node {
        private Integer id;
        private Integer status;
        private Integer parentId;
        private String reason;

        public Node(Integer id, Integer status, Integer parentId, String reason) {
            this.id = id;
            this.status = status;
            this.parentId = parentId;
            this.reason = reason;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "id=" + id +
                    ", status=" + status +
                    ", parentId=" + parentId +
                    ", reason=" + reason +
                    '}';
        }
    }


    public static void main(String[] args) throws ParseException, IOException, ExecutionException, InterruptedException,
            InvocationTargetException, IllegalAccessException {

        String imageUrl = "http://643108e7617ef.cdn.sohucs.com/03c5793ce4f048b482431a11164e5faa.jpg";
        if (imageUrl.contains("643108e7617ef.cdn.sohucs.com")) {
            imageUrl = imageUrl.replaceFirst("643108e7617ef.cdn.sohucs.com", "deliver.bjcnc.scs.sohucs.com");
        }
        System.out.println("imageUrl===========>" + imageUrl);

        String taold = "广告素材批量审核通过:[100000774, 100000777, 100000775, 100000776, 100000768, 100000771, 100000769]";
        Integer creativeSetid = 100000777;
        if (taold.contains(creativeSetid.toString())) {
            System.out.println("------------------------------------>");
        }

        System.out.println("5|9===========>" + (5 | 9));

        List<Integer> alist = new ArrayList<>();
/*        alist.add(1);alist.add(2);
        alist.add(3);  */
        List<Integer> blist = new ArrayList<>();
/*        blist.add(2);
        blist.add(3);*/
        blist.removeAll(alist);
        System.out.println("------------------->" + blist.toString());


        //String intern方法
        Date now = new Date();
        System.out.println(DateUtil.date2str(now, DateUtil.DEFAULT_DATE_PATTERN));

        String s1 = new String("1");
        s1.intern();
        String s2 = "1";
        System.out.println(s1 == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
        System.out.println(s3.intern() == s4.intern());

        //格式化时间
        String time = "2018-08-08";
        Date dataTime = DateUtil.str2date(time, "yyyy-MM-dd");
        String startTime = DateUtil.date2str(dataTime, "yyyy-MM-dd") + " 00:00:00";
        String endTime = DateUtil.date2str(dataTime, "yyyy-MM-dd") + " 23:59:59";
        System.out.println("dataTime======================" + dataTime + " " + startTime + " " + endTime);

        //开始时间和结束时间，24时格式显示
        Date start = DateUtil.str2date("2018-08-14 00:00:00", DateUtil.GENERAL_DATE_PATTERN);
        Date end = DateUtil.str2date("2018-08-14 23:59:59", DateUtil.GENERAL_DATE_PATTERN);
        int s = DateUtil.getHourOfDate(start);
        int e = DateUtil.getHourOfDate(end);
        List<Integer> list = new ArrayList<>();
        for (int i = s; i <= e; i++) {
            list.add(i);
        }
        System.out.println(list);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 24; i++) {
            boolean f = false;
            for (int j = 0; j < list.size(); j++) {
                if (i == list.get(j)) {
                    sb.append(1);
                    f = true;
                }
            }
            if (!f) sb.append(0);
        }
        System.out.println(Integer.parseInt(sb.toString(), 2));

        //二进制转化十进制或运算
        int ds = Integer.parseInt("000000000000000000000000", 2);
        int dm = Integer.parseInt("10", 2);
        int dd = ds | dm;
        System.out.println(Integer.toBinaryString(dd));
        String toBinaryString= Integer.toBinaryString(dd);
        int length = toBinaryString.length();
        StringBuffer sba = new StringBuffer();
        for(int i=0;i<(24-length);i++){
            sba.append("0");
        }
        System.out.println(sba.toString()+toBinaryString);


        //队列删除重复数字（保留第一个）
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(0);
        linkedList.add(0);
        linkedList.add(1);
        linkedList.add(0);
        linkedList.add(1);
        linkedList.add(1);
        linkedList.add(0);
        System.out.println(linkedList);

        LinkedList<Integer> resultList = new LinkedList<>();
        for (int i = 0; i < linkedList.size(); i++) {
            for (int j = i + 1; j < linkedList.size(); j++) {
                if (!linkedList.get(i).equals(linkedList.get(j))) {
                    resultList.add(linkedList.get(i));
                    i = j - 1;
                    break;
                }
            }
            if (i + 1 >= linkedList.size()) {
                resultList.add(linkedList.get(i));
            }
        }
        System.out.println(resultList);

        //配对开始结束时间
        List<String> listStr = new ArrayList<String>();
        for (int i = 0; i < resultList.size(); i++) {
            boolean f = false;
            if (resultList.get(i) == 1 && i == 0) {
                listStr.add("0点到1");
                continue;
            }
            int j = i + 1;
            if (j < resultList.size()) {
                listStr.add(resultList.get(i) + ":" + resultList.get(j));
                f = true;
                i = j;
            }
            if (!f) {
                if (resultList.get(i) == 0) {
                    listStr.add(resultList.get(i) + ":最后一秒");
                }
            }
        }
        for (int k = 0; k < listStr.size(); k++) {
            System.out.println(listStr.get(k));
        }


        LinkedList<Node> nodeList = new LinkedList<Node>();
        nodeList.add(new Node(11, 0, 0,""));
        nodeList.add(new Node(1, 1, 0,"A"));
        nodeList.add(new Node(3, 1, 0,"B"));
        nodeList.add(new Node(31, 1, 0,"B1"));
        nodeList.add(new Node(32, 1, 0,"B2"));
        nodeList.add(new Node(33, 1, 0,"B3"));
        nodeList.add(new Node(4, 0, 0,""));
        nodeList.add(new Node(5, 1, 0,"D"));
        nodeList.add(new Node(6, 0, 0,""));
        nodeList.add(new Node(61, 0, 0,""));
        nodeList.add(new Node(7, 1, 0,"G"));
        nodeList.add(new Node(8, 1, 0,"H"));
        nodeList.add(new Node(9, 0, 0,""));
        System.out.println("----->" + nodeList);

//        List<Integer> removeIds = new ArrayList<Integer>();
//        for (int i = 0; i < nodeList.size(); i++) {
//            Integer parentId = nodeList.get(i).getParentId();
//            Integer id = nodeList.get(i).getId();
//            if (parentId != 0) {
//                for (int j = i - 1; j >= 0; j--) {
//                    Node node = nodeList.get(j);
//                    if (node.getStatus() == 0 && !parentId.equals(node.getId())) {
//                        removeIds.add(id);
//                        break;
//                    }
//                    if(node.getStatus() == 0){
//                        break;
//                    }
//                }
//            }
//        }
//        System.out.println("------------->" + removeIds);
//
//        Iterator<Node> iterator = nodeList.iterator();
//        while (iterator.hasNext()){
//            Integer id = iterator.next().getId();
//            if (removeIds.contains(id)){
//                iterator.remove();
//            }
//        }
//        System.out.println(nodeList);
        LinkedList<Node> resultList2 = new LinkedList<Node>();
        for (int i = 0; i < nodeList.size(); i++) {
            for (int j = i + 1; j <= nodeList.size(); j++) {
                if (j == nodeList.size() || !nodeList.get(i).getStatus().equals(nodeList.get(j).getStatus())) {
                    resultList2.add(nodeList.get(i));
                    i = j - 1;
                    break;
                }else {
                    Node nodei = nodeList.get(i);
                    String nodej = nodeList.get(j).getReason();
                    nodei.setReason(nodei.getReason() + " " + nodej );
                }
            }
        }
        System.out.println(resultList2);

//        Process process = null;
//        long timeLong = System.currentTimeMillis();
//        //phantomjs js params
//        String cmdPrefix = null;
//        if(timeLong % 5 == 0){
//            cmdPrefix = "/opt/phantomjs/bin/phantomjs /opt/phantomjs/mult/screenshot.fill.mult.js";
//        }else if(timeLong % 5 == 1){
//            cmdPrefix = "/data/phantomjs-v2/bin/phantomjs /data/phantomjs-v2/mult/screenshot.fill.mult.js";
//        }else if(timeLong % 5 == 2){
//            cmdPrefix = "/data/phantomjs-v3/bin/phantomjs /data/phantomjs-v3/mult/screenshot.fill.mult.js";
//        }else if(timeLong % 5 == 3){
//            cmdPrefix = "/data/phantomjs-v4/bin/phantomjs /data/phantomjs-v4/mult/screenshot.fill.mult.js";
//        }else if(timeLong % 5 == 4){
//            cmdPrefix = "/data/phantomjs-v5/bin/phantomjs /data/phantomjs-v5/mult/screenshot.fill.mult.js";
//        }
//        System.out.println("==================="+cmdPrefix);
//
//        LinkedList<Integer> linked = new LinkedList<>();
//        linked.add(1);
//        linked.add(2);
//        linked.add(3);
//        linked.add(4);
//        linked.add(5);
//        List a = new ArrayList();
//        List b = new ArrayList();
//        a.removeAll(b);
//        ListIterator fwd = linked.listIterator();
//        ListIterator rev = linked.listIterator(linked.size());
//        for (int i=0, mid=linked.size()>>1; i<mid; i++) {
//            Object tmp = fwd.next();
//            System.out.println(tmp);
//            fwd.set(rev.previous());
//            rev.set(tmp);
//        }
//
//
//        System.out.println(linked);
//        System.out.println(linked.getLast());
//        Collections.reverse(linked);
//        System.out.println(linked);
//
//
//        Map<Integer,Integer> map = new HashMap<>();
//        map.put(1,11);
//        map.put(2,22);
//        map.put(3,33);
//        map.put(4,44);
//        map.put(5,55);
//        map.put(1,66);
//        System.out.println("----------"+map.values());
//        final ConcurrentHashMap<Integer, AtomicLong> consumeMap = new ConcurrentHashMap<Integer, AtomicLong>();
//        TestThread t = new TestThread(consumeMap);
//        ExecutorService pool = Executors.newFixedThreadPool(8);
//        for (int i = 0; i < 10000; i++) {
//            int finalI = i;
//            pool.submit(t);
//        }
//        //System.out.println("=====1=2=22==="+consumeMap);
//        pool.shutdown();
//
//        while (true) {
//            if (pool.isTerminated()) {
//
//                System.out.println("=====1=2=22===" + consumeMap);
//                break;
//            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        int endIndex = 154440805;
//        int lastIndex = 154445805;
//        List<Integer> indexList = new ArrayList<>();
//        do {
//            endIndex += 100000;
//            if (endIndex >= lastIndex) {
//                endIndex = lastIndex;
//                indexList.add(endIndex);
//                break;
//            }
//            indexList.add(endIndex);
//        } while (endIndex <= lastIndex);
//        System.out.println(indexList);
//        int startIndex = 327675;
//        ExecutorService pool = Executors.newFixedThreadPool(8);
//        for (Integer i : indexList) {
//            int finalStartIndex = startIndex;
//            pool.submit(new Runnable() {
//                @Override
//                public void run() {
//                    synchronized(consumeMap){
//                        AtomicLong aLong = consumeMap.get(1);
//                        System.out.println(aLong);
//                        if (null != aLong) {
//                            consumeMap.put(1, new AtomicLong(aLong.addAndGet(1)));
//                        } else {
//                            consumeMap.put(1, new AtomicLong(0));
//                        }
//                    }
//                }
//            });
//            startIndex = i;
//        }

//        List<Integer> aaaList = new ArrayList<>();
//        List<Integer> bbbList = new ArrayList<>();
//        bbbList.add(1);
//        bbbList.add(2);
//        bbbList.add(3);
//        bbbList.add(14);
//        aaaList.removeAll(bbbList);
//        System.out.println(aaaList.toString());
//
//
//        try {
//           boolean ibl =  exceptionMethod();
//        }catch (IOException e){
//            logger.error(e.getMessage());
//            logger.error(e.toString());
//            logger.error(e.getLocalizedMessage());
//            e.printStackTrace();
//
//        }
//
//        List<Integer> all = new ArrayList<>();
//        List<Integer> oth = new ArrayList<>();
//        for (int i=0;i<100;i++){
//            all.add(i);
//        }
//        for (int i=0;i<10;i++){
//            oth.add(i);
//        }
//        all = removeElements(all,oth);
//        System.out.println(DateUtil.date2str(new Date(),"yyyy-MM-dd HH:00:00")+"----(@*&@*&@*&@*&*&@&*@"+all.toString());
//
//
//        Long l1 = 1L;
//        Long l2 = 1L;
//        Long l3 = 1L;
//        Long l4 = 1L;
//        if(!l1.equals(l2)||!l1.equals(l3)||!l1.equals(l4)){
//            System.out.println("&&&&&&&&&&&&&&&&&&& not");
//        }else {
//            System.out.println("&&&&&&&&&&&&&&&&&&&");
//        }
//
//        Map<Integer, Long> aaMap = new HashMap<Integer, Long>();
//        updateMap(aaMap, 1, 1L);
//        updateMap(aaMap, 2, 2L);
//        updateMap(aaMap, 1, 2L);
//        System.out.println(aaMap.get(1));
//        System.out.println(aaMap.get(2));
//
//        Double numerator = CaculateUtil.subtract(0.44, 0.53);
//        Double denominator = 0.53;
//        Double ctrLinkRelative = CaculateUtil.divide(numerator * 100, denominator, 2);
//        System.out.println(ctrLinkRelative);
//
//        List<Integer> listList = new ArrayList<Integer>();
//        listList.add(1);
//        listList.add(2);
//        listList.add(3);
//        listList.add(4);
//        listList.add(5);
//        listList.add(6);
//        listList.add(7);
//        listList.add(8);
//        listList.add(9);
//        listList.add(10);
//        listList.add(1010);
//
//        Double dl1 = 0D;
//        Double dl2 = 0.0;
//        if(dl1.equals(dl2)){
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//        }else {
//            System.out.println("###################################");
//        }
//
//
//        Integer groupPage = 0;
//        int pageSize = 5;
//        int pageTotal = (int) Math.ceil(listList.size() * 1.0 / pageSize);
//        int size = listList.size();
//        while (groupPage < pageTotal) {
//            List<Integer> idList = null;
//            int endIndex = (groupPage + 1) * pageSize;
//            if (endIndex > size) {
//                idList = listList.subList(groupPage * pageSize, size);
//            } else {
//                idList = listList.subList(groupPage * pageSize, endIndex);
//            }
//            groupPage++;
//
//            if (CollectionUtils.isEmpty(idList)) {
//                continue;
//            }
//        }
//        ThreadLocal<String> local = new ThreadLocal<String>();
//        local.set("local");
//        local.remove();
//        System.gc();
//        System.out.println(local.get());
//
////        String ab = null;
////        try {
////            String url = "https://my.tv.zongcc.com/user/250342905";
////            BufferedReader in = new BufferedReader(new InputStreamReader(new URL(url).openConnection().getInputStream(), "UTF-8"));
////            while ((ab = in.readLine()) != null) {
////                System.out.println(ab);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//
////        Map<Integer,Long> map = new HashMap<Integer,Long>();
////        updateMap(map,1,1000L);
////        updateMap(map,2,2000L);
////        System.out.println("map ............."+map.toString());
////
////        List<Integer> liss = new ArrayList<>();
////        for(int i=0;i<24;i++){
////            liss.add(i);
////        }
////        int size = (int) Math.round(liss.size()*0.1);
////        System.out.println(liss.subList(0,size));
//
//
////        ExecutorService pool1 = Executors.newFixedThreadPool(2);
////        for(int i=0;i<10;i++){
////            final int finalI = i;
////            pool1.submit(new Runnable() {
////                @Override
////                public void run() {
////                    try {
////                        TimeUnit.SECONDS.sleep(1);
////                        System.out.println("pool...."+ finalI);
////                    } catch (InterruptedException e) {
////
////                    }
////                }
////            });
////        }
////        pool1.shutdown();
////        if(pool1.isShutdown()){
////            System.out.println("pool1.isShutdown()");
////        }
////        pool1.awaitTermination(1,TimeUnit.MINUTES);
////        if(pool1.isTerminated()){
////            System.out.println("pool1.isTerminated()");
////        }
//
//
////        List<Integer> integerList = new ArrayList<Integer>();
////        integerList.add(1);
////        integerList.add(2);
////        integerList.add(3);
////        integerList.add(19);
////        integerList.add(73);
////        integerList.add(736);
////        Collections.sort(integerList, new Comparator<Integer>() {
////            @Override
////            public int compare(Integer o1, Integer o2) {
////                return o2.compareTo(o1);
////            }
////        });
////        int startIndex =5 ;
////        int pageSizes = 5;
////        if(startIndex+pageSizes>integerList.size()){
////            integerList = integerList.subList(startIndex,integerList.size());
////        }else {
////            integerList = integerList.subList(startIndex,startIndex+pageSizes);
////        }
////        System.out.println(JacksonUtil.toJson(integerList));
//
//
////        int pageSize = 10;
////        Boolean groupFlag = true;
////        int listSize = 100;
////        while (groupFlag) {
////            if(listSize<pageSize){
////                groupFlag = false;
////            }
////            System.out.println("................. 执行");
////            listSize = listSize - 10;
////        }
////        System.out.println(".............执行结束");
//
//
////        String childrenSrc = "https://5ea9ef9fa2e97.cdn.zcc.com/fb/75/17/ee/134f9a08b413078821db1f01e9dcbdb9.png";
////        int index = childrenSrc.indexOf("com")+3;
////        String imageUrl = "http://artemis.bjcnc.scs.zcc.com"+childrenSrc.substring(index);
////        System.out.println("11111111  "+imageUrl);
//
//
//        /**
//         * 集合对象声明为final指的是引用不能被更改(new)，但是你可以向其中增加，删除或者改变内容
//         */
////        final Map<String,Integer> map = new HashMap<String,Integer>();
////        final Set<Integer> store = new HashSet<Integer>();
////        map.put("1",1);
////        map.put("2",2);
////        ExecutorService threadPool = Executors.newFixedThreadPool(2);
////        for(int i=0;i<1000;i++){
////            threadPool.submit(new Runnable() {
////                @Override
////                public void run() {
////                    Integer i1 = map.get("1");
////                    Integer i3 = map.get("2");
////                    store.add(i1);
////                    store.add(i3);
////                    System.out.println(store.toString());
////                }
////            });
////        }
////        threadPool.shutdown();
//
//        List<Integer> listTemp = new ArrayList<>();
//        for (int i = 0; i < 100000; i++) {
//            listTemp.add(i);
//        }
//        Long sttime = System.currentTimeMillis();
//        for (Integer i : listTemp) {
//            if (10000 == i) {
//                System.out.println("-----100000000000--------------");
//                break;
//            }
//        }
//        Long sttimeEnd = System.currentTimeMillis();
//        System.out.println(sttimeEnd - sttime);
//        CollectionUtils.filter(listTemp, new Predicate() {
//            @Override
//            public boolean evaluate(Object o) {
//                if ((int) o == 10000) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        });
//        System.out.println(System.currentTimeMillis() - sttimeEnd);
//        System.out.println(Arrays.asList(listTemp).toString());
//
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        Future f1 = pool.submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                TimeUnit.SECONDS.sleep(10);
//                return 111;
//            }
//        });
//        Future f2 = pool.submit(new Callable<Integer>() {
//            @Override
//            public Integer call() throws Exception {
//                return 222;
//            }
//        });
//        while (true) {
//            if (f1.isDone() && f2.isDone()) {
//                System.out.println("11111111------------------------ " + ((int) f1.get() + (int) f2.get()));
//                pool.shutdown();
//                break;
//            }
//        }
//
//
//        List list = new ArrayList<Seckill>();
//        Seckill seckill = new Seckill();
//        seckill.setName("test");
//        seckill.setCreateTime(new Date());
//        seckill.setNumber(1);
//        list.add(seckill);
//        String str = JacksonUtil.toJson(list);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
//
//        byte[] bytes = IOUtils.toByteArray(byteArrayInputStream);
//        List<Seckill> seckills = JacksonUtil.toCollection(new String(bytes), new TypeReference<List<Seckill>>() {
//        });
//        for (Seckill s : seckills) {
//            System.out.println("out object ========" + s.getName());
//        }
//
//        //获取系统类型
//        System.out.println("-----os name--------" + System.getProperty("os.name"));
//
//        String costStr = CaculateUtil.getLongDivide(36013528L, 100000L, 5);
//        Double costDouble = 0D;
//        Double cost = 108.38328D + 251.75200D;
//        if (StringUtils.isNotBlank(costStr)) {
//            costDouble = Double.valueOf(costStr);
//        }
//        if (!costDouble.equals(cost)) {
//            System.out.println("costDouble =========不想动相等");
//        } else {
//            System.out.println("costDouble =========相等");
//        }
//
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
//        System.out.println(sdf.format(new Date()) + ":00:00");
//
//
//        /*
//        计算操作
//         */
//        BigDecimal total = BigDecimal.TEN;
//
//        BigDecimal a = null;
//        if (null != a && a.compareTo(BigDecimal.ZERO) > 0) {
//            total = total.add(a);
//        }
//        BigDecimal b = new BigDecimal("10");
//        if (null != b && b.compareTo(BigDecimal.ZERO) > 0) {
//            total = total.add(b);
//        }
//        System.out.println(total);
//
//        Long l = 10L;
//        System.out.println(total.add(BigDecimal.valueOf(l)).longValue());
//
//
//        /*
//        日期计算
//         */
//        Calendar cale = null;
//        // 获取当月第一天和最后一天
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String firstday, lastday;
//        // 获取前月的第一天
//        cale = Calendar.getInstance();
//
//        cale.add(Calendar.MONTH, 0);
//        cale.set(Calendar.DAY_OF_MONTH, 1);
//        firstday = format.format(cale.getTime());
//        // 获取前月的最后一天
//        cale = Calendar.getInstance();
//        cale.add(Calendar.MONTH, 1);
//        cale.set(Calendar.DAY_OF_MONTH, 0);
//        lastday = format.format(cale.getTime());
//        System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String dateString = formatter.format(new Date());
//        System.out.println("今日 ： " + dateString);
//        Long cashBalance = 10L;
//        Long returnBalance = 70L;
//
//        if ((null == cashBalance || cashBalance.equals(0L)) && (null == returnBalance || returnBalance.equals(0L))) {
//            System.out.println(11111111);
//        } else {
//            System.out.println(2222);
//        }
//
//        System.out.println(cashBalance.compareTo(returnBalance));
//
//        ExecutorService executorService = Executors.newFixedThreadPool(20);
//        long start = System.currentTimeMillis();
//        final VolatileExample volatileExample = new VolatileExample();
//        volatileExample.set(0L);
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100000000; i++)
//                    volatileExample.getIncrement();
//                System.out.println("volatileExample " + volatileExample.get());
//            }
//        });
//        System.out.println("time----->" + (System.currentTimeMillis() - start));
//        executorService.shutdown();
//
//
//        VolatileExample volatileExample2 = new VolatileExample();
//        long start2 = System.currentTimeMillis();
//        for (int i = 0; i < 100000000; i++)
//            volatileExample2.getIncrement();
//        System.out.println("volatileExample2 " + volatileExample.get());
//        System.out.println("time2----->" + (System.currentTimeMillis() - start2));
//
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("123.126.70.239");
//        sb.append("zh36.163.com");
//        sb.append("1");
//        sb.append("1");
//        sb.append("kSyWTavkekWXVQRo".substring(8));
//        String mySign = EncryptUtil.EncoderByMD5(sb.toString(), "UTF-8");
//        System.out.println("mySign==========" + mySign);
//
//        //判断时间
//        Date current = new Date();
//        String dateStr = "2017-01-01";
//        Date date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd"});
//        System.out.println("============================" + current.equals(date));
//
//        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
//        Matcher matcher = pattern.matcher("海贼王12Vv。。");
//        System.out.println("中文、英文、数字但不包括下划线等符号 " + matcher.matches());
//
//        String regEx = "[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher matchers = p.matcher("ads_20170830_difff.tsv");
//        Date dateTime = DateUtil.str2date(matchers.replaceAll("").trim(), "yyyyMMdd");
//        System.out.println("------------" + DateUtil.date2str(dateTime));
//
//
//        System.out.println("=======================================================================");
//        File file = new File("C:\\zongcc.ppk");
//        //BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
//        FileInputStream ins = new FileInputStream(file);
//        FileInputStream in = new FileInputStream(file);
//        //BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
//        String code = "GBK";
//        /*if (bb[0] == -17 && bb[1] == -69 && bb[2] == -65){
//            code = "UTF-8";
//        }*/
//        //String charset =  FileCharsetUtil.getFileEncode(in);
//        //Charset charset =  Charset.forName(new TikaEncodingDetector().guessEncoding(in));
//        //Charset charset =  CharsetToolkit.guessEncoding(file, 4096);
//        //BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(ins,charset));
//        //BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(ins,charset.name()));
//        BufferedReader bufferedReader = new BufferedReader(new UnicodeReader(ins, FileCharsetUtil.getFileEncode(in)));
//        //InputStreamReader inputStreamReader = new InputStreamReader(in,code);
//        //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
////        String str = bufferedReader.readLine();
////        while(str != null){
////            System.out.println(str.trim());
////            str = bufferedReader.readLine();
////        }
//
//        Pattern patterns = Pattern.compile("^[\\u4E00-\\u9FA5A-Za-z0-9]+$");
//        String line = bufferedReader.readLine();
//        while (StringUtils.isNotBlank(line)) {
//            if (patterns.matcher(line).matches()) {
//                System.out.println("正常字符 " + line);
//                line = bufferedReader.readLine();
//            } else {
//                System.out.println("特殊字符 " + line);
//                break;
//            }
//        }
//        in.close();
//        bufferedReader.close();
//
//        FileInputStream inst = new FileInputStream(file);
//        System.out.println("文件编码 " + Charset.forName(new TikaEncodingDetector().guessEncoding(inst)));

    }

}
