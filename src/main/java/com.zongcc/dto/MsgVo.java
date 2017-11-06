package com.zongcc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * 统一的接口返回对象模板
 * Created by binhuang200996 on 2017/4/14.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MsgVo implements Serializable {

    private static final long serialVersionUID = 7192707670675018587L;
    public static final Integer SUCCESS_CODE = 1000;
    public static final Integer ERROR_CODE = 1001;

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
