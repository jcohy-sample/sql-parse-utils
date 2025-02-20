package com.xuanwu.demo.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/3/7 11:04
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "sql")
public class SQLProperties {

    /**
     * 转换后的路径，必须为绝对路径
     */
    private String location;


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
