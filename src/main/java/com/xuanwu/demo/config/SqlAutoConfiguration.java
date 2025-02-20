package com.xuanwu.demo.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuanwu.demo.SQLParseUtils;
import com.xuanwu.demo.classpath.ClassPathResource;
import com.xuanwu.demo.classpath.ClassPathUtils;
import com.xuanwu.demo.domain.DataBases;
import com.xuanwu.demo.domain.DbTableInfo;
import com.xuanwu.demo.props.SQLProperties;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/3/7 11:06
 * @since 1.0.0
 */
@EnableConfigurationProperties(SQLProperties.class)
public class SqlAutoConfiguration {

    @Bean
    public SQLParseUtils parse(SQLProperties properties, ObjectMapper mapper, JdbcTemplate template) {
        Set<ClassPathResource> names = ClassPathUtils.findClassPathResource(properties.getLocation());
        SQLParseUtils utils = new SQLParseUtils();
        try{
            for(ClassPathResource name : names) {
                if(name.getFilename().endsWith(".xml")) {
                    String sqlJson = FileCopyUtils.copyToString(name.read());
                    DataBases db = mapper.readValue(sqlJson, DataBases.class);
                    utils.parseCreateStatements(db)
                            .forEach(template::execute);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return utils;
    }
}
