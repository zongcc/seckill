package com.zongcc.controller;

import java.io.Serializable;

/**
 * Created by chunchengzong on 2017-10-25.
 */
public class ScreenShotChildren implements Serializable {
    private String tagName;
    private ScreenShotParent screenShotParent;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public ScreenShotParent getScreenShotParent() {
        return screenShotParent;
    }

    public void setScreenShotParent(ScreenShotParent screenShotParent) {
        this.screenShotParent = screenShotParent;
    }
}
