package com.xuanwu.demo.visitor;

import java.util.List;

import com.xuanwu.demo.domain.DbTableColumnInfo;
import com.xuanwu.demo.domain.DbTableInfo;
import com.xuanwu.demo.visitor.SQLVisitorImpl;

/**
 * 描述: SqlServer 访问者，输出 SQLServer 语句.
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 11:38
 * @since 1.0.0
 */
public class SqlServerVisitor extends SQLVisitorImpl {

    public SqlServerVisitor(Appendable appendable) {
        super(appendable);
    }

    /**
     * 打印表名
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
     * 打印表名或字段名，使用 []
     * @param name name
     */
    @Override
    public void printNames(String name) {
        print('[');
        print(name);
        print(']');
    }

    /**
     * 输出注释，默认 schema 为 dbo
     * @param table table
     * @param column column
     * @param comment comment
     */
    private void printColumnComment(String table, String column, String comment) {
        if(comment != null) {
            println();
            println("EXEC sp_addextendedproperty");
            println("'MS_Description', N'" + comment + "',");
            println("'SCHEMA', N'dbo',");
            println("'TABLE', N'" + table + "',");
            print("'COLUMN', N'" + column.replaceAll("`", "") + "';");
            println();
        }
    }


    /**
     * SQLServer 的注释在外层，需要重写此方法，在内层不输出。
     * @param comment comment
     */
    @Override
    public void printComment(String comment) {
    }
}
