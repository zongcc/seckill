package com.zongcc.image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by chunchengzong on 2017-10-19.
 */
public class HtmlScreenShotUtil {
    /**
     * 网页截屏，并保存图片
     *
     * @param url    页面地址
     * @param output 保存图片名(不带后缀)
     */
    public static String screenShot(String url, String output) {
        String outPutPath = DKConstant.DK_PHANTOMJS_OUTPUTPATH + output;
        Runtime rt = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        try {
            String cmd = DKConstant.DK_PHANTOMJS_SHELLPATH + " " + DKConstant.DK_PHANTOMJS_JSPATH + " " + url + " " + outPutPath;
            Process process = rt.exec(cmd);
            InputStream is = process.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tmp = "";
            try {
                while ((tmp = br.readLine()) != null) {
                    sb.append(tmp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        //String url = DKConstant.DK_PHANTOMJS_WEB_URL + "3631";
        long start = System.currentTimeMillis();
        String url = "https://www.baidu.com/";
        String imgName = System.currentTimeMillis() + ".png";

        // 保存图片
        String result = HtmlScreenShotUtil.screenShot(url, imgName);
        //  imgName = DK_PHANTOMJS_IMAGES + imgName; // 可直接访问的路径
        System.out.println(result);
        //linux系统中会返回数据（图片base64码和success标识结尾）
        if (result.endsWith("success")) {
            System.out.println("success");
        } else {
            System.out.println("保存图片失败");
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
