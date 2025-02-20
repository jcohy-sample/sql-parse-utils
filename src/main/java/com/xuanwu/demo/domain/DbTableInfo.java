package com.xuanwu.demo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.xuanwu.demo.visitor.SQLVisitor;
import com.xuanwu.demo.SQLObject;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/2/27 9:49
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DbTableInfo implements SQLObject {

    private String tableName;

    @JacksonXmlElementWrapper(localName = "columns")
    @JacksonXmlProperty(localName = "column")
    private List<DbTableColumnInfo> columns;

    @JacksonXmlElementWrapper(localName = "datas")
    @JacksonXmlProperty(localName = "data")
    private List<String> data;

    @JsonIgnore
    private List<String> foreign = new ArrayList<>();

    @JsonIgnore
    private List<String> dataSql = new ArrayList<>();

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<DbTableColumnInfo> getColumns() {
        return columns;
    }

    @Override
    public void accept(SQLVisitor visitor) {
        visitor.visit(this);
    }

    public void setColumns(List<DbTableColumnInfo> columns) {
        this.columns = columns;
    }

    public List<String> getForeign() {
        return foreign;
    }

    public void setForeign(List<String> foreign) {
        this.foreign = foreign;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getDataSql() {
        return dataSql;
    }

    public void setDataSql(List<String> dataSql) {
        this.dataSql = dataSql;
    }
}
