package com.xuanwu.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xuanwu.demo.domain.DbTableColumnInfo;
import com.xuanwu.demo.domain.DbTableInfo;
import com.xuanwu.demo.domain.DataBases;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 10:00
 * @since 1.0.0
 */
public class SQLTest {

    DataBases mysql;

    DataBases oracle;

    DataBases sqlserver;

    private SQLParseUtils utils = new SQLParseUtils();

    @BeforeEach
    void setUp() {
        mysql = createMysqlObject("mysql");
        oracle = createMysqlObject("oracle");
        sqlserver = createMysqlObject("sqlserver");
    }

    private DataBases createMysqlObject(String dbType) {
        DataBases db = new DataBases();
        db.setTargetDataBase(dbType);

        DbTableInfo table = new DbTableInfo();
        table.setTableName("saga_user");

        List<DbTableColumnInfo> columns = new ArrayList<>();
        DbTableColumnInfo column = new DbTableColumnInfo();
        column.setName("id");
        column.setComments("主键");
        column.setPrimaryKey(true);
        column.setForeignKey(true);
        column.setReferencedTable("user");
        column.setReferencedTableKey("id");
        column.setDefaultValue("1");
        if(Objects.equals(dbType, "oracle")) {
            column.setType("VARCHAR2");
            column.setTargetDataType("VARCHAR2(50)");
        } else if(Objects.equals(dbType, "mysql")) {
            column.setType("bigint");
            column.setTargetDataType("bigint(10)");
        } else if(Objects.equals(dbType,"sqlserver")) {
            column.setType("int");
            column.setTargetDataType("int");
        }

        column.setSort(1);
        columns.add(column);
        table.setColumns(columns);
        db.setTable(List.of(table));
        return db;
    }

    @Test
    void test01() {

        System.out.println("=================================================");
        List<String> mysqlStatement = utils.parseCreateStatements(mysql);
        System.out.println(mysqlStatement);

        System.out.println("=================================================");
        List<String> oracleStatement = utils.parseCreateStatements(oracle);
        System.out.println(oracleStatement);

        System.out.println("=================================================");
        List<String> sqlserverStatement = utils.parseCreateStatements(sqlserver);
        System.out.println(sqlserverStatement);

    }

    @Test
    void writeXml() throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(mysql);
        System.out.println(xml);
    }

    @Test
    void readXml() {

    }
}
