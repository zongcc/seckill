package com.zongcc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zongcc.dto.MsgVo;
import com.zongcc.dto.ScreenShotVo;
import com.zongcc.image.ScreenShotConstant;
import com.zongcc.model.ScreenShotChildren;
import com.zongcc.model.ScreenShotParent;
import com.zongcc.system.SpringContextHolder;
import com.zongcc.utils.JacksonUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
     * 批量构造截图使用的静态页面
     *
     * @return
     */
    @RequestMapping(value = "/freemarker")
    public String freemarker(@RequestBody String jsonStr, HttpServletRequest request, RedirectAttributes model) throws IOException {
        String logKey = RandomStringUtils.randomAlphanumeric(10);
        logger.info("===ScreenShotController freemarker === call params === logKey:{},jsonStr:{}",
                new Object[]{logKey, StringUtils.isBlank(jsonStr) ? "" : jsonStr});
        //参数为空判断
        if (StringUtils.isBlank(jsonStr)) {
            logger.info("===ScreenShotController freemarker === param jsonStr is null === logKey:{}", new Object[]{logKey});
            return "500";
        }

        //构造html
        List<ScreenShotVo> screenShotVoList = JacksonUtil.toCollection(jsonStr, new TypeReference<List<ScreenShotVo>>() {
        });
        List<Map> dataList = new ArrayList<Map>();
        for (ScreenShotVo screenShotVo : screenShotVoList) {
            StringBuffer sb = new StringBuffer();
            String width = screenShotVo.getWidth();
            String height = screenShotVo.getHeight();
            String group = screenShotVo.getGroup();
            ScreenShotParent parent = screenShotVo.getProps();
            List<ScreenShotChildren> childrenList = screenShotVo.getChildren();
            String className = parent.getClassName();
            String src = parent.getSrc();
            String style = parent.getStyle();
            sb.append("<div ");
            if (StringUtils.isNotBlank(className)) {
                sb.append(" class=" + className);
            }
            if (StringUtils.isNotBlank(src)) {
                sb.append(" src=" + src);
            }
            if (StringUtils.isNotBlank(style)) {
                sb.append(" style=" + style);
            }
            sb.append(" >");
            //添加中间样式
            for (ScreenShotChildren children : childrenList) {
                String tagName = children.getTagName();
                ScreenShotParent childrenProps = children.getProps();
                String childrenClassName = childrenProps.getClassName();
                String childrenSrc = childrenProps.getSrc();
                String childrenStyle = childrenProps.getStyle();
                String childrenText = childrenProps.getText();
                sb.append("<").append(tagName).append(" ");
                if (StringUtils.isNotBlank(childrenClassName)) {
                    sb.append(" class=" + childrenClassName);
                }
                if (StringUtils.isNotBlank(childrenSrc)) {
                    sb.append(" src=" + childrenSrc);
                }
                if (StringUtils.isNotBlank(childrenStyle)) {
                    sb.append(" style=" + childrenStyle);
                }
                sb.append(" >");
                if (StringUtils.isNotBlank(childrenText)) {
                    sb.append(childrenText);
                }
                sb.append("</").append(tagName).append(">");
            }
            sb.append("</div>");
            String templateLocation = "screenShot.vm";
            String divHtml = sb.toString();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("testDiv", divHtml);

            //生成静态文件
            String path = request.getSession().getServletContext().getRealPath("/") + ScreenShotConstant.SCREEN_SHOT_WEB_HTML_PATH;
            String htmlName = RandomStringUtils.randomNumeric(10) + ".html";
            String outPath = path.replace("/", File.separator) + htmlName;
            VelocityEngine velocityEngine = SpringContextHolder.getBean(VelocityEngine.class);
            String data = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", map);
            FileUtils.writeStringToFile(new File(outPath), data, "UTF-8");
            Map<String, String> dataMap = new HashMap<String, String>();
            dataMap.put("htmlPath", outPath);
            dataMap.put("htmlName", htmlName);
            dataMap.put("width", width);
            dataMap.put("height", height);
            dataMap.put("group", group);
            dataList.add(dataMap);
        }
        model.addFlashAttribute("dataList", dataList);
        logger.info("===ScreenShotController freemarker === dataList === logKey:{} dataList:{}", new Object[]{logKey,
                JacksonUtil.toJson(dataList)});
        return "redirect:/screen/shot";
    }

    /**
     * 批量截图并上传cdn返回给前端图片列表
     *
     * @param dataList
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/shot")
    @ResponseBody
    public MsgVo shot(@ModelAttribute("dataList") List<Map<String, String>> dataList) throws IOException {
        String logKey = RandomStringUtils.randomAlphanumeric(10);
        long startTime = System.currentTimeMillis();
        MsgVo msgVo = new MsgVo();
        //判断传入参数是否为空
        if (CollectionUtils.isEmpty(dataList)) {
            logger.info("===ScreenShotController shot === dataList is null === logKey:{}",
                    new Object[]{logKey});
            msgVo.setCode(MsgVo.ERROR_CODE);
            msgVo.setMsg("传入参数有误");
            return msgVo;
        }

        //批量创建图片
        String osName = System.getProperty("os.name");
        List<String> picUrls = new ArrayList<String>();
        final Base64 base64 = new Base64();
        Runtime rt = Runtime.getRuntime();
        for (Map<String, String> dataMap : dataList) {
            String htmlName = dataMap.get("htmlName");
            String width = dataMap.get("width");
            String height = dataMap.get("height");
            String group = dataMap.get("group");
            String htmlPath = dataMap.get("htmlPath");
            //判断系统添加前缀
            String url;
            if (osName.toLowerCase().startsWith("win")) {
                url = "file:///" + ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_WEB_URL + htmlName;
            } else {
                url = ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_WEB_URL + htmlName;
            }
            String imageName = System.currentTimeMillis() + "-" + group + "-" + width + "-" + height + ".png";
            StringBuilder sb = new StringBuilder();
            BufferedReader br = null;
            InputStream is = null;
            Process process = null;
            try {
                String cmd = ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_SHELLPATH + " " + ScreenShotConstant
                        .SCREEN_SHOT_PHANTOMJS_JSPATH + " " + url + " " + width + " " + height;
                logger.info("===ScreenShotController shot === exec command === logKey:{},command:{}",
                        new Object[]{logKey, cmd});
                process = rt.exec(cmd);
                is = process.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String tmp;
                try {
                    while ((tmp = br.readLine()) != null) {
                        sb.append(tmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //查询执行结果是否成功，失败返回错误
                if ("failed".equals(sb.toString())) {
                    logger.info("===ScreenShotController shot === screen shot  image fail === logKey:{},url:{},imageName:{}",
                            new Object[]{logKey, url, imageName});
                    msgVo.setCode(MsgVo.ERROR_CODE);
                    msgVo.setMsg("截图失败");
                    return msgVo;
                } else {
                    MultipartFile multipartFile = new MockMultipartFile("file",
                            imageName, ContentType.APPLICATION_OCTET_STREAM.toString(), base64.decode(sb.toString().getBytes()));
                    String imageUrl = null;
                    picUrls.add(imageUrl);
                    logger.info("===ScreenShotController shot === screen shot image success === logKey:{},imageUrl:{}",
                            new Object[]{logKey, imageUrl});
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != br) {
                    br.close();
                }
                if (null != is) {
                    is.close();
                }
                if (null != process) {
                    process.destroy();
                }
                //删除html文件
                File htmlFile = new File(htmlPath);
                htmlFile.delete();
            }
        }
        Map<String, Object> urlMap = new HashMap<String, Object>();
        urlMap.put("picUrls", picUrls);
        msgVo.setCode(MsgVo.SUCCESS_CODE);
        msgVo.setData(JacksonUtil.toJson(urlMap));
        long costTime = (System.currentTimeMillis() - startTime) / 1000;
        logger.info("===ScreenShotController shot === success return data === logKey:{},timeCost:{},msgVo:{}",
                new Object[]{logKey, costTime, JacksonUtil.toJson(msgVo)});
        return msgVo;
    }

    @RequestMapping(value = "/freemarker2")
    public String freemarker2(HttpServletRequest request, RedirectAttributes model)
            throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/") + ScreenShotConstant.SCREEN_SHOT_WEB_HTML_PATH;
        String templateLocation = "screenShot.vm";
        String divHtml = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("testDiv", divHtml);
        String fileName = RandomStringUtils.randomNumeric(10) + ".html";
        String htmlPath = path.replace("/", File.separator) + fileName;
        VelocityEngine velocityEngine = SpringContextHolder.getBean(VelocityEngine.class);
        String data = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", map);
        File hFile = new File(htmlPath);
        FileUtils.writeStringToFile(hFile, data, "UTF-8");
        model.addFlashAttribute("fileName", fileName);
        model.addFlashAttribute("htmlPath", htmlPath);
        return "redirect:/screen/shot2";
    }

    @RequestMapping(value = "/shot2")
    @ResponseBody
    public String shot2(@ModelAttribute("htmlPath") String htmlPath, @ModelAttribute
            ("fileName") String fileName) throws
            IOException {

        String osName = System.getProperty("os.name");
        String url;
        if (osName.toLowerCase().startsWith("win")) {
            url = "file:///" + ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_WEB_URL + fileName;
        } else {
            url = ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_WEB_URL + fileName;
        }
        Long startTime = System.currentTimeMillis();
        final Base64 base64 = new Base64();
        Runtime rt = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        InputStream is = null;
        Process process = null;
        for (int i = 0; i < 1; i++) {
            try {
                String output = System.currentTimeMillis() + ".png";
                String outPutPath = ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_OUTPUTPATH + output;
                String cmd = ScreenShotConstant.SCREEN_SHOT_PHANTOMJS_SHELLPATH + " " + ScreenShotConstant
                        .SCREEN_SHOT_PHANTOMJS_JSPATH + " " + url + " 500 500";
                System.out.println(cmd);
                process = rt.exec(cmd);
                is = process.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                String tmp;
                try {
                    while ((tmp = br.readLine()) != null) {
                        sb.append(tmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MultipartFile multipartFile = new MockMultipartFile("file",
                        output, ContentType.APPLICATION_OCTET_STREAM.toString(), base64.decode(sb.toString().getBytes()));
                String imageUrl = null;
                System.out.println(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                br.close();
                is.close();
                process.destroy();
                //删除生产的html文件
                File file = new File(htmlPath);
                file.delete();
            }
        }
        System.out.println((System.currentTimeMillis() - startTime) / 1000);
        return "success";
    }


}
