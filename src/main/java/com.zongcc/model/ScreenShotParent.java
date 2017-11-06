package com.zongcc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 *
 * @author chunchengzong
 * @date 2017-10-25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenShotParent implements Serializable {
    /**
     * html中样式
     */
    private String style;
    /**
     * html中class
     */
    private String className;
    /**
     * html中图片的地址
     */
    private String src;

    /**
     * 文本
     */
    private String text;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
