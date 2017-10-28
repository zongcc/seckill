package com.zongcc.controller;

import java.io.Serializable;

/**
 * Created by chunchengzong on 2017-10-25.
 */
public class ScreenShotParent implements Serializable {
    private String style;
    private String className;
    private String src;

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
}
