package com.xuanwu.demo;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xuanwu.demo.classpath.ClassPathResource;
import com.xuanwu.demo.classpath.ClassPathUtils;
import com.xuanwu.demo.domain.DataBases;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.FileCopyUtils;

/**
 * 描述: .
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/3/7 11:50
 * @since 1.0.0
 */
@SpringBootTest(classes = TestApplication.class)
@Import(DataSourceConfiguration.class)
@TestMethodOrder(OrderAnnotation.class)
public class SqlApplicationTest {

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlJdbcTemplate;

    @Qualifier("oracleJdbcTemplate")
    @Autowired
    private JdbcTemplate oracleJdbcTemplate;

    @Qualifier("sqlServerJdbcTemplate")
    @Autowired
    private JdbcTemplate sqlServerJdbcTemplate;

    @Test
    void test01() {
        Set<ClassPathResource> names = ClassPathUtils.findClassPathResource("db");
        SQLParseUtils utils = new SQLParseUtils();
        XmlMapper mapper = new XmlMapper();
        try{
            for(ClassPathResource name : names) {
                if(name.getFilename().endsWith(".xml")) {
                    String sqlSource = FileCopyUtils.copyToString(name.read());
                    DataBases tableInfo = mapper.readValue(sqlSource, DataBases.class);
                    List<String> sqls = utils.parseCreateStatements(tableInfo);
                    if(tableInfo.getTargetDataBase().equals("mysql")) {
                        for(String sql : sqls) {
                            System.out.println("======= mysql =======");
                            System.out.println(sql);
//                            mysqlJdbcTemplate.execute(sql);
                        }
                    }

                    if(tableInfo.getTargetDataBase().equals("oracle")) {
                        for(String sql : sqls) {
                            System.out.println("=======  oracle =======");
                            System.out.println(sql);
//                            oracleJdbcTemplate.execute(sql);
                        }
                    }

                    if(tableInfo.getTargetDataBase().equals("sqlserver")) {
                        for(String sql : sqls) {
                            System.out.println("=======  sqlserver =======");
                            System.out.println(sql);
//                            sqlServerJdbcTemplate.execute(sql);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
