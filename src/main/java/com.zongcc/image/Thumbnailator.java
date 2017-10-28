package com.zongcc.image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chunchengzong on 2017-10-18.
 */
public class Thumbnailator {
    private static String path = "D:\\";

    public static void main(String[] args) throws Exception {
        //缩放
        //Thumbnails.of(path + "1.jpg").size(100, 100).toFile(path + "2.jpg");
//        Thumbnails.of(path + "1.jpg").size(2560, 2048).toFile(path + "3.jpg");

        //按照比例缩放
       Thumbnails.of(path + "1.jpg").scale(0.2).toFile(path + "3.jpg");
//        Thumbnails.of(path + "1.jpg").scale(1.2).toFile(path + "3.jpg");

        //keepAspectRatio(false) 默认是按照比例缩放的(zcc)
        //Thumbnails.of(path + "1.jpg").size(200, 200).keepAspectRatio(true).toFile(path + "/thumb_noscale_blue.jpg");

        //旋转
//        Thumbnails.of(path + "1.jpg").size(644, 322).rotate(90).toFile(path + "/thumb_rotate90_blue.jpg");
//        Thumbnails.of(path + "1.jpg").size(644, 322).rotate(-90).toFile(path + "/thumb_rotate-90_blue.jpg");

        //水印
//        Thumbnails.of(path + "1.jpg").size(644, 322).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(path +
//                "/default.jpg")), 0.5f).toFile(path + "/thumb_watermarkbr_blue.jpg");
//        ;
//        Thumbnails.of(path + "1.jpg").size(644, 322).watermark(Positions.CENTER, ImageIO.read(new File(path +
//                "/default.jpg")), 0.8f).toFile(path + "/thumb_watermarkc_blue.jpg");

        //裁剪
//        Thumbnails.of(path + "1.jpg").sourceRegion(Positions.CENTER, 400, 400).size(200, 200).keepAspectRatio(false).toFile(path + "/thumb_regioncenter_blue.jpg");
//        Thumbnails.of(path + "1.jpg").sourceRegion(Positions.BOTTOM_RIGHT, 400, 400).size(200, 200).keepAspectRatio(false).toFile(path + "/thumb_regionbr_blue.jpg");

        //转换图像格式
//        Thumbnails.of(path + "1.jpg").size(644, 322).outputFormat("png").toFile(path + "4.png");
//        Thumbnails.of(path + "1.jpg").size(644, 322).outputFormat("gif").toFile(path + "5.gif");
//        Thumbnails.of(path + "1.jpg").size(644, 322).outputFormat("JPEG").toFile(path + "6.jpeg");

        //输出到outputstream中
//        OutputStream outputStream = new FileOutputStream(new File(path + "2.gif"));
//        Thumbnails.of(path + "1.jpg").size(644, 322).toOutputStream(outputStream);

        //asBufferedImage() 返回BufferedImage
//        BufferedImage thumbnail = Thumbnails.of(path + "1.jpg").size(644, 322).asBufferedImage();
//        ImageIO.write(thumbnail, "jpg", new File(path + "2.jpg"));

    }
}
