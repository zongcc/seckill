package com.zongcc.image;


import com.zongcc.utils.PropertiesUtil;

import java.io.File;

public class ScreenShotConstant {
    /**
     * 项目域名
     */
    private static final String SCREEN_SHOT_DOMIN = PropertiesUtil.getInstance("app.properties").getValue("app.domain");

    /**
     * phantomJS 配置
     */
    private static final String SCREEN_SHOT_PHANTOMJS_PATH = PropertiesUtil.getInstance("app.properties").getValue("phantomjs.path");
    public static final String SCREEN_SHOT_WEB_HTML_PATH = PropertiesUtil.getInstance("app.properties").getValue("web.html.path");
    /**
     * phantomJS 脚本路径
     */
    private static final String PHANTOMJS_SHELL_PATH = PropertiesUtil.getInstance("app.properties").getValue("phantomjs.shell");
    public static final String SCREEN_SHOT_PHANTOMJS_SHELLPATH = (SCREEN_SHOT_PHANTOMJS_PATH + PHANTOMJS_SHELL_PATH)
            .replace("/", File.separator);
    /**
     * phantomJS js路径
     */
    private static final String PHANTOMJS_JS_PATH = PropertiesUtil.getInstance("app.properties").getValue("phantomjs.js");
    public static final String SCREEN_SHOT_PHANTOMJS_JSPATH = (SCREEN_SHOT_PHANTOMJS_PATH + PHANTOMJS_JS_PATH).replace("/", File.separator);
    /**
     * phantomJS 截图保存路径
     */
    private static final String PHANTOMJS_IMAGE_PATH = PropertiesUtil.getInstance("app.properties").getValue("phantomjs.image.path");
    public static final String SCREEN_SHOT_PHANTOMJS_OUTPUTPATH = (SCREEN_SHOT_PHANTOMJS_PATH + PHANTOMJS_IMAGE_PATH).replace("/", File.separator);
    /**
     * phantomJS 页面地址
     */
    public static final String SCREEN_SHOT_PHANTOMJS_WEB_URL = (SCREEN_SHOT_DOMIN + SCREEN_SHOT_WEB_HTML_PATH).replace("/", File.separator);
}