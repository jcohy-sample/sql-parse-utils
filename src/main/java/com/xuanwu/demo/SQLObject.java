package com.xuanwu.demo;

import com.xuanwu.demo.visitor.SQLVisitor;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/24 10:14
 * @since 1.0.0
 */
public interface SQLObject {
    void accept(SQLVisitor visitor);
}
