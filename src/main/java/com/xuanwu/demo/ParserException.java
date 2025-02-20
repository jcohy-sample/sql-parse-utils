package com.xuanwu.demo;

import java.io.Serializable;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 11:26
 * @since 1.0.0
 */
public class ParserException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ParserException() {
    }

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable e) {
        super(message, e);
    }

    public ParserException(String message, int line, int col) {
        super(message);
    }

    public ParserException(Throwable ex, String ksql) {
        super("parse error. detail message is :\n" + ex.getMessage() + "\nsource sql is : \n" + ksql, ex);
    }
}
