package com.xuanwu.demo.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * @version 1.0.0 2023/2/27 9:50
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DbTableColumnInfo implements SQLObject, Comparable<DbTableColumnInfo> {

    @JacksonXmlProperty(isAttribute = true)
    private String name;

    @JacksonXmlProperty(isAttribute = true)
    private String comments;

    @JacksonXmlProperty(isAttribute = true)
    private String type;

    @JacksonXmlProperty(isAttribute = true)
    private String defaultValue;

    @JacksonXmlProperty(isAttribute = true)
    private boolean primaryKey;

    @JacksonXmlProperty(isAttribute = true)
    private boolean foreignKey;

    @JacksonXmlProperty(isAttribute = true)
    private String referencedTable;

    @JacksonXmlProperty(isAttribute = true)
    private String referencedTableKey;

    @JacksonXmlProperty(isAttribute = true)
    private int sort;

    @JacksonXmlProperty(isAttribute = true)
    private String targetDataType;

    @JacksonXmlProperty(isAttribute = true)
    private boolean ignoreData;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void accept(SQLVisitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public boolean isForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(boolean foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getReferencedTable() {
        return referencedTable;
    }

    public void setReferencedTable(String referencedTable) {
        this.referencedTable = referencedTable;
    }

    public String getReferencedTableKey() {
        return referencedTableKey;
    }

    public void setReferencedTableKey(String referencedTableKey) {
        this.referencedTableKey = referencedTableKey;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTargetDataType() {
        return targetDataType;
    }

    public void setTargetDataType(String targetDataType) {
        this.targetDataType = targetDataType;
    }

    @Override
    public int compareTo(DbTableColumnInfo that) {
        return this.getSort() - that.getSort();
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isIgnoreData() {
        return ignoreData;
    }

    public void setIgnoreData(boolean ignoreData) {
        this.ignoreData = ignoreData;
    }
}
