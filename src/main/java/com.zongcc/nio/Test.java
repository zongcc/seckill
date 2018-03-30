package com.zongcc.nio;

import com.zongcc.utils.ImageUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class Test {

    public static void main(String[] args) throws InterruptedException, IOException {
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

        String imageUrl = "http://images.zcc.com/saf/a2017/1123/ChAKr1oWkaSAdjF8AADBu3-IPfY875644x322.jpg";
        String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
        String urlPath = "d:\\" + RandomStringUtils.randomAlphanumeric(20) + ext;


        try {
            BufferedImage image = ImageIO.read(new URL(imageUrl).openStream());
            ImageIO.write(image, "jpg", new File(urlPath));
            ImageUtil.saveUrlAs(imageUrl,urlPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean saveUrlAs(String fileUrl, String savePath)/*fileUrl网络资源地址*/ {

        try {
            URL url = new URL(fileUrl);/*将网络资源地址传给,即赋值给url*/
            /*此为联系获得网络资源的固定格式用法，以便后面的in变量获得url截取网络资源的输入流*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            DataInputStream in = new DataInputStream(connection.getInputStream());
            /*此处也可用BufferedInputStream与BufferedOutputStream*/
            DataOutputStream out = new DataOutputStream(new FileOutputStream(savePath));
            /*将参数savePath，即将截取的图片的存储在本地地址赋值给out输出流所指定的地址*/
            byte[] buffer = new byte[4096];
            int count = 0;
            while ((count = in.read(buffer)) > 0)/*将输入流以字节的形式读取并写入buffer中*/ {
                out.write(buffer, 0, count);
            }
            out.close();/*后面三行为关闭输入输出流以及网络资源的固定格式*/
            in.close();
            connection.disconnect();
            return true;/*网络资源截取并存储本地成功返回true*/

        } catch (Exception e) {
            System.out.println(e + fileUrl + savePath);
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