package com.kedacom.keda.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Self defined exception class
 */
public class MyException extends RuntimeException {

    private Integer code;

    public MyException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
