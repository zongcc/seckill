package com.zongcc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zongcc.utils.JacksonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chunchengzong on 2016-09-21.
 */
@Controller
@RequestMapping("/screen")
public class ScreenShotController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public static final String DK_PHANTOMJS_JSPATH = DK_PHANTOMJS_PATH + "examples/rasterize2.js";
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

    /**
     * 获取秒杀列表
     *
     * @return
     */
    @RequestMapping(value = "/freeMaker")
    @ResponseBody
    public String freeMaker(@RequestBody String jsonStr, HttpServletRequest request, RedirectAttributes model) throws
            IOException,
            InterruptedException {
        if (StringUtils.isBlank(jsonStr)) {
            return "fail";
        }

        String path = request.getSession().getServletContext().getRealPath("/");
        File dir = new File(path);
        if (!dir.exists()) {//判断目录是否存在，不存在创建
            dir.mkdir();
        }

        List<ScreenShotVo> screenShotVoList = JacksonUtil.toCollection(jsonStr, new TypeReference<List<ScreenShotVo>>() {
        });

        List<Map> dataList = new ArrayList<Map>();
        for (ScreenShotVo screenShotVo : screenShotVoList) {
            StringBuffer sb = new StringBuffer();
            String width = screenShotVo.getWidth();
            String height = screenShotVo.getHeight();
            String group = screenShotVo.getGroup();
            ScreenShotParent parent = screenShotVo.getProps();
            List<ScreenShotChildren> childrens = screenShotVo.getChildren();
            String className = parent.getClassName();
            String src = parent.getSrc();
            String style = parent.getStyle();
            sb.append("<div ");
            if (StringUtils.isNotBlank(className)) {
                sb.append("class=" + className);
            }
            if (StringUtils.isNotBlank(src)) {
                sb.append("src=" + src);
            }
            if (StringUtils.isNotBlank(style)) {
                sb.append("style=" + style);
            }
            sb.append(" >");
            //添加中间样式
            for (ScreenShotChildren children : childrens) {
                String tagName = children.getTagName();
                ScreenShotParent childrenProps = children.getScreenShotParent();
                String childrenClassName = childrenProps.getClassName();
                String childrenSrc = childrenProps.getSrc();
                String childrenStyle = childrenProps.getStyle();
                sb.append("<").append(tagName).append(" ");
                if (StringUtils.isNotBlank(childrenClassName)) {
                    sb.append("class=" + childrenClassName);
                }
                if (StringUtils.isNotBlank(childrenSrc)) {
                    sb.append("src=" + childrenSrc);
                }
                if (StringUtils.isNotBlank(childrenStyle)) {
                    sb.append("style=" + childrenStyle);
                }
                sb.append(" >").append("</").append(tagName).append(">");
            }
            sb.append("</div>");
            String templateLocation = "ad.vm";
            String divHtml = sb.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("testDiv", divHtml);
            String fileName = RandomStringUtils.randomNumeric(10) + "-" + group + ".html";
            String outpath = path + "\\screen\\" + fileName;
            VelocityEngine velocityEngine = SpringContextHolder.getBean(VelocityEngine.class);
            String data = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", map);
            FileUtils.writeStringToFile(new File(outpath), data, "UTF-8");
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("name", outpath);
            dataMap.put("fileName", fileName);
            dataMap.put("width", width);
            dataMap.put("height", height);
            dataList.add(dataMap);
        }
        //Thread.sleep(100);
        model.addFlashAttribute("dataList", dataList);
        return "redirect:/screen/shot";
    }

    @RequestMapping(value = "/shot")
    @ResponseBody
    public String shot(@ModelAttribute("name") String name, @ModelAttribute("fileName") String fileName) throws IOException {
        String url = "http://localhost:8000/screen/" + fileName;
        String output = System.currentTimeMillis() + ".png";
        String outPutPath = DK_PHANTOMJS_OUTPUTPATH + output;
        Runtime rt = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        InputStream is = null;
        Process process = null;
        try {
            String cmd = DK_PHANTOMJS_SHELLPATH + " " + DK_PHANTOMJS_JSPATH + " " + url + " " + outPutPath + " 500 500";
            process = rt.exec(cmd);
            is = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
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
        } finally {
            br.close();
            is.close();
            process.destroy();
        }
        System.out.println(sb.toString());
        return "success";
//        File file = new File(name);
//        System.out.println("=========="+file.isFile());
//        file.delete();


//        BASE64Decoder decoder = new BASE64Decoder();
//        try {
//        // 解密
//        byte[] b = decoder.decodeBuffer(sb.toString());
//        // 处理数据
//        for (int i = 0; i < b.length; ++i) {
//            if (b[i] < 0) {
//                b[i] += 256;
//                }
//            }
//        OutputStream out = new FileOutputStream("D:/png.png");
//        out.write(b);
//        out.flush();
//        out.close();
//        } catch (Exception e) {
//        e.printStackTrace();
//        }

    }


}
