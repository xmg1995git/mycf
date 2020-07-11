package com.test.mycf.exception;

/**
 * @author ASUS
 * @create 2020/7/11 - 10:38
 */
public class MyException extends RuntimeException {

    private ResultEnum resultEnum;

    public MyException(){}

    public MyException(ResultEnum resultEnum){
        super(resultEnum.getMessage());
    }
}
