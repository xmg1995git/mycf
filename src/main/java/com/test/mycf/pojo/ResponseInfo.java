package com.test.mycf.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.org.apache.xerces.internal.dom.PSVIAttrNSImpl;

import java.io.Serializable;

/**
 * @author ASUS
 * @create 2020/7/6 - 13:11
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseInfo implements Serializable {

    private String code;

    private String message;

    private Object body;

    /**
     * 成功 状态码
     */
    private static final String SUCCESS_CODE = "200";
    /**
     * 警告 / 错误 状态码
     */
    private static final String WARNING_CODE = "400";

    public ResponseInfo() {
    }

    public ResponseInfo(String code, String message, Object body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public ResponseInfo success() {
        this.code = SUCCESS_CODE;
        return this;
    }

    /**
     * 成功
     * 状态码:200
     * @param body
     * @return body
     */
    public ResponseInfo success(Object body) {
        this.code = SUCCESS_CODE;
        this.body = body;
        return this;
    }

    public ResponseInfo warning() {
        this.code = WARNING_CODE;
        return this;
    }

    /**
     * 警告 / 错误
     * 状态码:400
     * @param body
     * @return body
     */
    public ResponseInfo warning(Object body) {
        this.code = WARNING_CODE;
        this.body = body;
        return this;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }


}
