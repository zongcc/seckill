package com.zongcc.staticTest;

import com.zongcc.utils.JacksonUtil;
import org.apache.commons.lang.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chunchengzong
 * @date 2018-03-21 11:23
 **/
public class DownloadUrlImg {
    public static void main(String[] args) throws UnsupportedEncodingException {

        int index = 1;
        int times = (int) Math.ceil(90 * 1.0 / 30);
        do {
            System.out.println("1111111111111111111111111111111");
            index++;
        }while (index <= times);


        //下载图片到本地
        //String urlPath = SOURCE_IMAGE_PATH + RandomStringUtils.randomAlphanumeric(20) + ".jpg";
//        String urlPath = "d:/" + RandomStringUtils.randomAlphanumeric(20) + ".jpg";
//            /*InputStream in = new URL(imageUrl).openStream();
//            Files.copy(in, Paths.get(urlPath.replace("/", File.separator)));*/
//        String imageUrl = "https://643108e7617ef.cdn.zongcc.com/a6cc5a0e669a4849b02cf8829f00d52b.jpg";
//        if(imageUrl.contains("https")){
//            imageUrl = imageUrl.replace("https","http");
//        }
//        System.out.println(imageUrl);
//        try {
//            BufferedImage image = ImageIO.read(new URL(imageUrl).openStream());
//            ImageIO.write(image,"jpg",new File(urlPath));
//        }catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<Map> listTemp = new ArrayList<Map>();
//        Map map = new HashMap();
//        map.put("imageWidth",1180);
//        map.put("imageHeight",100);
//        map.put("canvasWidth",960);
//        map.put("canvasHeight",90);
//        map.put("url","/opt/phantomjs/js/default.jpg");
//        Map map1 = new HashMap();
//        map1.put("imageWidth",1180);
//        map1.put("imageHeight",100);
//        map1.put("canvasWidth",960);
//        map1.put("canvasHeight",100);
//        map1.put("url","/opt/phantomjs/js/default.jpg");
//        listTemp.add(map);
//        listTemp.add(map1);
//        String params = URLEncoder.encode(JacksonUtil.toJson(listTemp), "UTF-8");
//        System.out.println(params);
//
//        String url = "https://86c0d0f3e1ce0.cdn.zcc.com/b33f841d46d0486bac23350b9e34a6d3.jpg";
//        String path = "d:/pic.jpg";
//        downloadPicture(url, path);
//
//        int i = url.lastIndexOf('.');
//        System.out.println(url.substring(i+1));
//
//        try {
//            InputStream in = new URL(url).openStream();
//            Files.copy(in, Paths.get("d:/pic2.jpg"));
//        } catch (MalformedURLException e) {
//
//        } catch (IOException e) {
//
//        }
//
//        try {
//            URL url1 = new URL(url);
//            BufferedImage image = ImageIO.read(url1);
//            ImageIO.write(image,"png",new File("d:/pic3.png"));
//            ImageIO.write(image,"jpg",new File("d:/pic4.jpg"));
//
////            File newFile = new File("d:/pic3.png");
////            BufferedImage newImage = ImageIO.read(new FileInputStream(newFile));
////            System.out.println(newImage.getWidth());
////            System.out.println(newImage.getHeight());
////            System.out.println(newFile.length());
//        }catch (MalformedURLException e) {
//
//        } catch (IOException e) {
//
//        }


    }

    //链接url下载图片
    private static void downloadPicture(String imageUrl, String path) {
        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(new File(path));

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}