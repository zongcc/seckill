package com.zongcc.nio;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zongcc.utils.DateUtil;
import com.zongcc.utils.HttpTookit;
import com.zongcc.utils.JacksonUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {

        Date date = new Date();
        String date2str = DateUtil.date2str(date, DateUtil.DEFAULT_DATE_HOUR_MINUTE_PATTERN);
        System.out.println(date2str + "00" + "------" + date2str + "30");


        LinkedList<Integer> mapValue = new LinkedList<>();
        mapValue.add(1);
        mapValue.add(0);
        mapValue.add(1);
        Integer first = mapValue.peekFirst();
        while (null != first && first.equals(1)) {
            mapValue.removeFirst();
            first = mapValue.peekFirst();
        }

        System.out.println(mapValue.toString());

        String pvJson = HttpTookit.doGet("http://pv.mp.sohuno.com/stat/medias/323504?date=20181112", null);

        // warn:通过passport获取pv失败
        if (StringUtils.isBlank(pvJson) || pvJson.equals("[null]")) {
            System.out.println("pvJson =================> null");
        }

        /*
        解析返回json获得pv
        pv = pcEv + pcPv + wapEv + wapPv + appEv + appPv
         */
        JSONArray articleCntArray = JSONArray.fromObject(pvJson);
        JSONObject articleCntObject = articleCntArray.getJSONObject(0);
        Integer pcEv = articleCntObject.getInt("pcEv");
        Integer pcPv = articleCntObject.getInt("pcPv");
        Integer wapEv = articleCntObject.getInt("wapEv");
        Integer wapPv = articleCntObject.getInt("wapPv");
        Integer appEv = articleCntObject.getInt("appEv");
        Integer appPv = articleCntObject.getInt("appPv");
        System.out.println(pcEv + pcPv + wapEv + wapPv + appEv + appPv);

        System.out.println(URLEncoder.encode("90%的家长都不知道，给老师送礼的意义！", "UTF-8").replaceAll("\\+", "%20"));
        System.out.println(URLDecoder.decode("90%25%E7%9A%84%E5%AE%B6%E9%95%BF%E9%83%BD%E4%B8%8D%E7%9F%A5%E9%81%93%EF%BC%8C%E7%BB%99%E8%80%81" +
                "%E5%B8%88%E9%80%81%E7%A4%BC%E7%9A%84%E6%84%8F%E4%B9%89%EF%BC%81", "UTF-8"));

        String adapterValue = "离异女，找{0_city}男友，直接约";
        Pattern pattern = Pattern.compile("(?<=\\{)[^\\}]+");
        Matcher matcher = pattern.matcher(adapterValue);
        List<String> adapterList = new ArrayList<String>();
        while (matcher.find()) {
            adapterList.add(matcher.group());
        }
        System.out.println(adapterList);

        List<Integer> aList = new ArrayList<>();
        List<Integer> bList = new ArrayList<>();
        aList.add(111);
        aList.add(222);
        aList.add(333);
        bList.add(111);
        bList.add(222);
        //bList.add(333);
        //bList.add(4444);
        System.out.println(aList.containsAll(bList));
        System.out.println(aList.retainAll(bList));
        String json = JacksonUtil.toJson(aList);
        System.out.println(json);
        System.out.println(JacksonUtil.toCollection(json, new TypeReference<List<Integer>>() {
        }));
        System.out.println(CollectionUtils.union(aList, bList));


        //Thumbnails.of("d:\\QQ.jpg").scale(1f).outputQuality(0.4f).toFile("d:\\QQ_1.jpg");
//        File imageFile = new File("d:\\QQ.jpg");
//        File compressedImageFile = new File("d:\\QQ_0.5.jpg");
//
//        InputStream inputStream = new FileInputStream(imageFile);
//        OutputStream outputStream = new FileOutputStream(compressedImageFile);
//
//        float imageQuality = 0.5f;
//
//        //Create the buffered image
//        BufferedImage bufferedImage = ImageIO.read(inputStream);
//
//        //Get image writers
//        Iterator<ImageWriter> imageWriters = ImageIO.getImageWritersByFormatName("jpg");
//
//        if (!imageWriters.hasNext())
//            throw new IllegalStateException("Writers Not Found!!");
//
//        ImageWriter imageWriter = (ImageWriter) imageWriters.next();
//        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
//        imageWriter.setOutput(imageOutputStream);
//
//        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
//
//        //Set the compress quality metrics
//        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//        imageWriteParam.setCompressionQuality(imageQuality);
//
//        //Created image
//        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
//
//        // close all streams
//        inputStream.close();
//        outputStream.close();
//        imageOutputStream.close();
//        imageWriter.dispose();


        //String path = "/opt/project/mp.xlsx";

//        Integer integer = 1;
//        Long lgg = 1L;
//        if(integer.equals(lgg)){
//            System.out.println("11111111111111");
//        }else {
//            System.out.println("2222222222222222222222222");
//        }
//
//        Double dob = 566666.6667D;
//        Long lg = 566666L;
//        System.out.println(dob.doubleValue());
//        System.out.println(dob.longValue());
//        System.out.println(lg.doubleValue());
//        System.out.println(lg.longValue());
//
//        List<Integer> pAccounts = new ArrayList<Integer>();
//        List<Integer> allAccounts = new ArrayList<Integer>();
//        allAccounts.add(1);
//        allAccounts.add(2);
//        allAccounts.add(3);
//        allAccounts.add(4);
//        allAccounts.add(5);
//        pAccounts.add(1);
//        pAccounts.add(2);
//        pAccounts.add(3);
//        allAccounts.removeAll(pAccounts);
//        System.out.println("----------------"+allAccounts.toString());
//
//        Date yesterDay = DateUtil.addDays(new Date(),-1);
//        String date = DateUtil.date2str(yesterDay,"yyyyMMdd");
//        System.out.println(date);
//
//        String str = "ads_20170718.tsv";
//        //str.split("-");
//        int a = str.indexOf("ads_");
//        int b = str.indexOf(".tsv");
//        System.out.println(str.substring(a+"ads_".length(),b));
//        System.out.println("中间的日期"+DateUtil.str2date(str.substring(4,b),"yyyyMMdd"));
//        //System.out.println("asdfasdfads  "+Integer.valueOf(str));
//       // System.out.println("asdfasdfads  "+ new Integer(str));
//
//        String path = "C:\\Users\\chunchengzong\\Desktop\\keys.txt";
//        BufferedReader br = null;
//        try {
//            File file = new File(path);
//            //if(file.exists()) file.delete();
//            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
//
//            String line = br.readLine();
//            while (StringUtils.isNotBlank(line)) {
//                //System.out.println("======"+line);
////                String[] array = line.split("\t");
////                System.out.println("======"+array[0]);
////                System.out.println("======"+array[1]);
////                System.out.println("======"+array[2]);
////                System.out.println("======"+array[3]);
////                System.out.println("======"+array[4]);
////                System.out.println("======"+array[5]);
////                System.out.println("======"+array[6]);
//                System.out.println("======"+line);
//
//                System.out.println("++++++++++++++++++++++++++++");
//                line = br.readLine();
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        List<Integer> list = new ArrayList<>();
//        list.add(1);list.add(2);list.add(3);list.add(4);list.add(5);
//        for(Integer i:list){
//            try {
//                excuteMethod(i);
//            }catch (Exception e){
//                e.printStackTrace();
//                continue;
//            }
//            System.out.println("===== "+i);
//        }
//
//        String aL="ads_20170120_diff.tsv";
//        String regEx="[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(aL);
//        String alm = m.replaceAll("").trim();
//        System.out.println(alm.trim());
//
//
//    }
//
//    private static void excuteMethod(int i) throws InterruptedException {
//        if(i==2){
//            Thread.sleep(100);
//            int j = 1/1;
//        }else {
//            Thread.sleep(100);
//        }

        //getPicQuality("d:\\aa.jpg", 10240L);
        //Thumbnails.of("d:\\640x1136_3.jpg").scale(1f).outputQuality(0.7f).toFile("d:\\640x1136_3.jpg");

//        List<Integer> groupIdList = new ArrayList<>();
//        do {
//            groupIdList.add(1);
//        }while (groupIdList.size()<5036);
//        int PAGE_SIZE = 5000;
//        Integer page = 0;
//        int i = (int) Math.ceil(groupIdList.size() * 1.0 / PAGE_SIZE);
//        int groupSize = groupIdList.size();
//        while (page < i) {
//            List<Integer> idList = null;
//            int endIndex = (page + 1) * PAGE_SIZE;
//            if (endIndex > groupSize) {
//                idList = groupIdList.subList(page * PAGE_SIZE, groupSize);
//            } else {
//                idList = groupIdList.subList(page * PAGE_SIZE, endIndex);
//            }
//            page++;
//        }

//        String imageUrl = "http://images.zcc.com/saf/a2017/1123/ChAKr1oWkaSAdjF8AADBu3-IPfY875644x322.jpg";
//        String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
//        String urlPath = "d:\\" + RandomStringUtils.randomAlphanumeric(20) + ext;
//
//
//        try {
//            BufferedImage image = ImageIO.read(new URL(imageUrl).openStream());
//            ImageIO.write(image, "jpg", new File(urlPath));
//            ImageUtil.saveUrlAs(imageUrl,urlPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Calendar instance = Calendar.getInstance();
//        int seconds = instance.get(Calendar.SECOND);
//        System.out.println(instance.getTime());
//        System.out.println(seconds);
//        while (seconds != 0) {
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+seconds);
//            TimeUnit.SECONDS.sleep(1);
//            Calendar calendar = Calendar.getInstance();
//            seconds = calendar.get(Calendar.SECOND);
//        }
//        System.out.println("###############");
//        ExecutorService pool = Executors.newFixedThreadPool(13);
//        for(int i=0;i<13;i++){
//            final int finalI = i;
//            pool.submit(new Runnable() {
//                @Override
//                public void run() {
//                       System.out.println(finalI);
//                }
//            });
//        }
//        pool.shutdown();
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        for (int i = 0; i < 10; i++) {
//            pool.submit(new Runnable() {
//                @Override
//                public void run() {
//                    String random = RandomStringUtils.randomAlphabetic(10);
//                    saveUrlAs("https://643108e7617ef.cdn.sohucs.com/2f409611589748168c915d3f0ea51de7.jpg", "d:\\" + random + ".jpg");
//                }
//            });
//        }
//        pool.shutdown();
        /*Thumbnails.of("d:\\aaa.jpg")
                .size(800, 200).rotate(90)
                .toFile("d:\\a380_200x300.jpg");
        Thumbnails.of("d:\\aaa.jpg")
                .size(800, 200).rotate(-90)
                .toFile("d:\\-a380_200x300.jpg");*/
        /*Thumbnails.of("d:\\aaa.jpg")
                .size(1280,1024)
                .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("d:\\default.jpg")),0.1f)
                .outputQuality(0.8f)
                .toFile("d:\\a380_watermark_bottom_right.jpg");

        Thumbnails.of("d:\\aaa.jpg")
                .size(1280,1024)
                .watermark(Positions.BOTTOM_LEFT,ImageIO.read(new File("d:\\default.jpg")),0.1f)
                .outputQuality(0.8f)
                .toFile("d:\\a380_watermark_center.jpg");*/
    }


    public static boolean saveUrlAs(String fileUrl, String savePath)/*fileUrl网络资源地址*/ {

        try {
            URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/
            /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2);
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                DataInputStream in = new DataInputStream(connection.getInputStream());
                /*此处也可用BufferedInputStream与BufferedOutputStream*/
                DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
                /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
                byte[] buffer = new byte[4096];
                int count = 0;
                while ((count = in.read(buffer)) != -1)/*将输入流以字节的形式读取并写入buffer中*/ {
                    out.write(buffer, 0, count);
                }
                out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/
                in.close();
            }
            connection.disconnect();
            return true;/*网络资源截取并存储本地成功返回true*/

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getPicQuality(String fromPic, Long toSize) {
        //图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
        File imageFile = new File(fromPic.replace("/", File.separator));
        Long length = imageFile.length();
        String toPic = fromPic;
        float quality = 1f;
        try {
            while (length.longValue() > toSize.longValue()) {
                quality = quality - 0.05f;
                if (quality < 0.5f) {
                    break;
                }
                Thumbnails.of(fromPic).scale(1f).outputQuality(quality).toFile(toPic);
                File tempFile = new File(toPic.replace("/", File.separator));
                length = tempFile.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toPic;
    }


}