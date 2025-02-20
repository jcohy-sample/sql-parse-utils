package com.xuanwu.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.xuanwu.demo.domain.DataBases;
import com.xuanwu.demo.domain.DbTableInfo;
import com.xuanwu.demo.visitor.MysqlVisitor;
import com.xuanwu.demo.visitor.OracleVisitor;
import com.xuanwu.demo.visitor.SQLVisitor;
import com.xuanwu.demo.visitor.SqlServerVisitor;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 11:31
 * @since 1.0.0
 */
public class SQLParseUtils {

    public String parseCreateStatement(DbTableInfo db,SQLVisitor visitor){
        StringBuilder out = new StringBuilder();
        db.accept(visitor);
        return out.toString();
    }
    public List<String> parseCreateStatements(DataBases db) {
        List<String> result = new ArrayList<>();

        // 解析创建语句
        db.getTable().forEach((table -> {
            StringBuilder out = new StringBuilder();
            SQLVisitor visitor = getVisitor(db, out);
            parseCreateStatement(table,visitor);
            result.add(out.toString());
        }));

        // 解析外键
        db.getTable().stream()
                .filter(table -> !CollectionUtils.isEmpty(table.getForeign()))
                .forEach((table -> table.getForeign().forEach((foreign) -> result.add(foreign))));

        db.getTable()
                .forEach(table -> result.addAll(table.getDataSql()));
        // 解析数据
        return result;
    }
    private static SQLVisitor getVisitor(DataBases db,StringBuilder out) {
        String dataBase = db.getTargetDataBase();
        if(dataBase.equalsIgnoreCase("mysql")) {
            return new MysqlVisitor(out);
        }

        if(dataBase.equalsIgnoreCase("oracle")) {
            return new OracleVisitor(out);
        }

        if(dataBase.equalsIgnoreCase("sqlserver")) {
            return new SqlServerVisitor(out);
        }
        throw new ParserException("不支持的数据库类型！");
    }
}
