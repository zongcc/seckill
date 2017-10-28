package com.zongcc.image;

/**
 * Created by chunchengzong on 2017-10-19.
 */
public class DKConstant {
    /**
     * 蛋壳域名
     */
    private static final String DK_DOMIN = "https://api-wxc.sf-rush.com/";
    /**
     * phantomJS 配置
     */
    //private static final String DK_PHANTOMJS_PATH = "/data/wwwroot/sftc.dankal.cn/phantomjs/";
    private static final String DK_PHANTOMJS_PATH = "D:\\phantomjs-2.1.1-windows\\phantomjs-2.1.1-windows\\";
    /**
     * phantomJS 脚本路径
     */
    public static final String DK_PHANTOMJS_SHELLPATH = DK_PHANTOMJS_PATH + "bin/phantomjs.exe";
    /**
     * phantomJS js路径
     */
    public static final String DK_PHANTOMJS_JSPATH = DK_PHANTOMJS_PATH + "examples/rasterize.js";
    /**
     * phantomJS 截图保存路径
     */
    public static final String DK_PHANTOMJS_OUTPUTPATH = DK_PHANTOMJS_PATH + "images/";
    /**
     * phantomJS 页面地址
     */
    public static final String DK_PHANTOMJS_WEB_URL = DK_DOMIN + "web/index.html?order_id=";
    /**
     * phantomJS 图片地址
     */
    public static final String DK_PHANTOMJS_IMAGES = DK_DOMIN + "phantomjs/images/";
}
