package com.xuanwu.demo.visitor;

import com.xuanwu.demo.domain.DbTableColumnInfo;
import com.xuanwu.demo.domain.DbTableInfo;
import com.xuanwu.demo.visitor.SQLVisitorImpl;

/**
 * 描述: Oracle 访问者，输出 Oracle 语句.
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 11:37
 * @since 1.0.0
 */
public class OracleVisitor extends SQLVisitorImpl {


    public OracleVisitor(Appendable appender) {
        super(appender);
    }

    /**
     * 输出创建语句
     * @param table table
     */
    @Override
    public void visit(DbTableInfo table) {
        super.visit(table);
        for (DbTableColumnInfo column : table.getColumns()) {
            this.printColumnComment(table.getTableName(), column.getName(), column.getComments());
        }
    }

    /**
     * 打印表名或字段名，使用双引号
     * @param name name
     */
    @Override
    public void printNames(String name) {
        print('"');
        print(name);
        print('"');
    }

    /**
     *  打印注释
     * @param table table
     * @param column column
     * @param comment comment
     */
    private void printColumnComment(String table, String column, String comment) {
        if (comment != null) {
            println();
            print("COMMENT ON COLUMN ");
            printNames(table);
            print(".");
            printNames(column);
            print(" IS '" + comment + "';");
        }
    }

    /**
     * Oracle 的注释在外层，需要重写此方法，在内层不输出。
     * @param comment comment
     */
    @Override
    public void printComment(String comment) {
    }
}
