package com.xuanwu.demo.visitor;

import com.xuanwu.demo.domain.DbTableColumnInfo;
import com.xuanwu.demo.domain.DbTableInfo;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/24 10:22
 * @since 1.0.0
 */
public interface SQLVisitor {

    void visit(DbTableInfo table);

    void visit(DbTableColumnInfo column);
}
