package com.zongcc.dto;


import com.zongcc.model.ScreenShotChildren;
import com.zongcc.model.ScreenShotParent;

import java.util.List;

/**
 * Created by chunchengzong on 2017-10-25.
 */
public class ScreenShotVo {

    /**
     * 图片宽度
     */
    private String width;
    /**
     * 图片高度
     */
    private String height;
    /**
     * 图片组数
     */
    private String group;
    /**
     * 父级属性
     */
    private ScreenShotParent props;
    /**
     * 子集属性
     */
    private List<ScreenShotChildren> children;

    /**
     * 生产html的绝对路径
     */
    private String htmlPath;

    /**
     * 图片的base64编码
     */
    private String image;

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ScreenShotParent getProps() {
        return props;
    }

    public void setProps(ScreenShotParent props) {
        this.props = props;
    }

    public List<ScreenShotChildren> getChildren() {
        return children;
    }

    public void setChildren(List<ScreenShotChildren> children) {
        this.children = children;
    }

    public String getHtmlPath() {
        return htmlPath;
    }

    public void setHtmlPath(String htmlPath) {
        this.htmlPath = htmlPath;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
