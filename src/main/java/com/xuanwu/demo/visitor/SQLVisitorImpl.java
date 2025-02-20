package com.xuanwu.demo.visitor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.xuanwu.demo.ParserException;
import com.xuanwu.demo.SQLUtils;
import com.xuanwu.demo.domain.DbTableColumnInfo;
import com.xuanwu.demo.domain.DbTableInfo;


/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 9:56
 * @since 1.0.0
 */
public class SQLVisitorImpl implements SQLVisitor {

    protected final Appendable appender;

    /**
     * 换行
     */
    protected transient int lines = 0;

    /**
     * 缩进
     */
    protected int indentCount = 0;

    /**
     * 定义整个输出语句流程
     * @param table table
     */
    @Override
    public void visit(DbTableInfo table) {
        print("CREATE TABLE ");

        // 打印表名
        printNames(table.getTableName());

        // 打印表列
        printTableColumns(table.getColumns());

        // 打印结尾
        printEnd();

        // 处理外键
        printForeignKey(table);

        // 处理数据
        printData(table);
    }

    private void printData(DbTableInfo table) {
        List<String> dataSql = table.getDataSql();

        List<DbTableColumnInfo> columns = table.getColumns().stream()
                .filter((column) -> !column.isIgnoreData())
                .sorted()
                .collect(Collectors.toList());

        table.getData().forEach((data) -> {
            StringBuilder out = new StringBuilder();
            out.append("INSERT INTO ");
            out.append(getNames(table.getTableName()));
            // 打印列名
            if (columns.size() > 0) {
                out.append('(');
                printColumns(columns,out);
                out.append(')');
            }
            // 打印值
            out.append(" VALUES (");
            out.append(data.replace("[","").replace("]",""));
            out.append(");");
            dataSql.add(out.toString());
        });
    }

    private void printColumns(List<DbTableColumnInfo> columns,StringBuilder out) {
        for (int i = 0, size = columns.size(); i < size; ++i) {
            if (i != 0) {
                out.append(", ");
            }
            out.append(columns.get(i).getName());
        }
    }

    public void printForeignKey(DbTableInfo table) {
    }


    /**
     * 打印字段
     * @param column column
     */
    @Override
    public void visit(DbTableColumnInfo column) {
        printNames(column.getName());
        print(" " + column.getTargetDataType());
        // 在 Oracle 中，default 必须放在非控约束前面
        printDefault(column);
        printPrimaryKey(column);
        printComment(column.getComments());
    }

    /**
     * 结尾语句
     */
    public void printEnd() {
        print(";");
    }

    /**
     * 打印注释
     * @param comment comment
     */
    public void printComment(String comment) {
        try {
            if (comment != null) {
                print(" COMMENT ");
                this.appender.append('\'').append(comment).append('\'');
            }
        }
        catch (IOException ex) {
            throw new ParserException("print error", ex);
        }
    }

    /**
     * 打印默认值
     * @param column column
     */
    public void printDefault(DbTableColumnInfo column) {
        String defaultValue = column.getDefaultValue();

        if(!Objects.isNull(defaultValue)) {
            print(" DEFAULT ");
            if (SQLUtils.isCharType(column.getType()) || defaultValue.trim().equals("")) {
                print("'" + defaultValue + "'");
            } else {
                print(Integer.parseInt(defaultValue));
            }
        }
    }

    /**
     * 打印主键
     * @param column column
     */
    public void printPrimaryKey(DbTableColumnInfo column) {
        if(column.getPrimaryKey()) {
            print(" PRIMARY KEY NOT NULL");
        }
    }

    /**
     * 打印列名，在有的场景中，需要把表名的 `` 符号转化为 "".
     * @param name name
     */
    public void printNames(String name) {
        print(name);
    }

    public String getNames(String name) {
        return name;
    }
    /**
     * 实际会调用 visit(SQLColumnDefinition column) 方法。
     * @param columns columns
     */
    public void printTableColumns(List<DbTableColumnInfo> columns) {
        int size = columns.size();

        if (size == 0) {
            return;
        }

        print(" (");
        this.indentCount++;
        println();

        Collections.sort(columns);

        for (int i = 0; i < size; i++) {
            DbTableColumnInfo columnInfo = columns.get(i);
            columnInfo.accept(this);
            if (i != size - 1) {
                print(',');
            }
            if (i != size - 1) {
                println();
            }
        }
        this.indentCount--;
        println();
        print(')');
    }

    public SQLVisitorImpl(Appendable appender) {
        this.appender = appender;
    }

    public Appendable getAppender() {
        return appender;
    }

    public void decrementIndent() {
        this.indentCount--;
    }

    public void incrementIndent() {
        this.indentCount++;
    }

    public void print(char value) {
        if (this.appender == null) {
            return;
        }
        try {
            this.appender.append(value);
        }
        catch (IOException ex) {
            throw new ParserException("print error", ex);
        }
    }

    public void print(int value) {
        if (this.appender == null) {
            return;
        }

        if (appender instanceof StringBuffer) {
            ((StringBuffer) appender).append(value);
        }
        else if (appender instanceof StringBuilder) {
            ((StringBuilder) appender).append(value);
        }
        else {
            print(Integer.toString(value));
        }
    }

    protected void print(String text) {
        if (this.appender == null) {
            return;
        }
        try {
            this.appender.append(text);
        }
        catch (IOException ex) {
            throw new ParserException("println error", ex);
        }
    }

    public void println(String text) {
        print(text);
        println();
    }

    public void println() {
        print('\n');
        lines++;
        printIndent();
    }

    /**
     * 缩进
     */
    protected void printIndent() {
        if (this.appender == null) {
            return;
        }
        try {
            for (int i = 0; i < this.indentCount; ++i) {
                this.appender.append('\t');
            }
        }
        catch (IOException ex) {
            throw new ParserException("print error", ex);
        }
    }
}
