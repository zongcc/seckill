package com.zongcc.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 *
 * @author chunchengzong
 * @date 2017-10-25
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScreenShotChildren implements Serializable {
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 标签中属性
     */
    private ScreenShotParent props;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public ScreenShotParent getProps() {
        return props;
    }

    public void setProps(ScreenShotParent props) {
        this.props = props;
    }
}
