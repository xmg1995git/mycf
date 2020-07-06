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

    private static final String SUCCESS_CODE = "200";
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

    public ResponseInfo success(Object body) {
        this.code = SUCCESS_CODE;
        this.body = body;
        return this;
    }

    public ResponseInfo warning(String code) {
        this.code = WARNING_CODE;
        return this;
    }

    public ResponseInfo warning(String code, Object body) {
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
