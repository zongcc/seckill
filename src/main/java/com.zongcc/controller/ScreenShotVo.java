package com.zongcc.controller;

import java.util.List;

/**
 * Created by chunchengzong on 2017-10-25.
 */
public class ScreenShotVo {
    private String width;
    private String height;
    private String group;
    private ScreenShotParent props;
    private List<ScreenShotChildren> children;

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
}
