package com.xuanwu.demo.domain;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/3/8 15:23
 * @since 1.0.0
 */
@JacksonXmlRootElement(localName = "databases")
public class DataBases {

    @JacksonXmlProperty(isAttribute = true)
    private String targetDataBase;

    @JacksonXmlElementWrapper(localName = "table",useWrapping = false)
    List<DbTableInfo> table;

    public List<DbTableInfo> getTable() {
        return table;
    }

    public void setTable(List<DbTableInfo> table) {
        this.table = table;
    }

    public String getTargetDataBase() {
        return targetDataBase;
    }

    public void setTargetDataBase(String targetDataBase) {
        this.targetDataBase = targetDataBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataBases dataBases = (DataBases) o;
        return Objects.equals(targetDataBase, dataBases.targetDataBase) && Objects.equals(table, dataBases.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetDataBase, table);
    }

    @Override
    public String toString() {
        return "DataBases{" +
                "targetDataBase='" + targetDataBase + '\'' +
                ", table=" + table +
                '}';
    }
}
