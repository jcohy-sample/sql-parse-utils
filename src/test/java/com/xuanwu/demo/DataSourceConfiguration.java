package com.xuanwu.demo;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 描述: .
 *
 * <p>
 * Copyright © 2023 <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
 *
 * @author jiac
 * @version 1.0.0 2023/1/9 15:21
 * @since 1.0.0
 */
@Configuration
public class DataSourceConfiguration {


	@Primary
	@Bean
	public JdbcTemplate mysqlJdbcTemplate() {
		DataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:3306/sql-parse?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true")
				.username("root")
				.password("jcohy1203")
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.build();
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate oracleJdbcTemplate() {
		DataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:oracle:thin:@localhost:1521:orcl")
				.username("SYSTEM")
				.password("jcohy1203")
				.driverClassName("oracle.jdbc.driver.OracleDriver")
				.build();
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public JdbcTemplate sqlServerJdbcTemplate() {
		DataSource dataSource = DataSourceBuilder.create()
				.url("jdbc:sqlserver://192.168.11.106:1433;DatabaseName=sql-parse")
				.username("sa")
				.password("123456")
				.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
				.build();
		return new JdbcTemplate(dataSource);
	}

}
