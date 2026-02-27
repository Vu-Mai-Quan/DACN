package com.example.dacn.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;


@EnableJpaRepositories(
	basePackages = "com.example.dacn.db1",
	entityManagerFactoryRef = "abstractEntityManagerFactoryBean",
	transactionManagerRef = "db1TrManager")
@Configuration
/**
 * @EnableTransactionManagement spring bật sẵn rồi, thường không cần bật;
 */
@RequiredArgsConstructor
public class Db1Config {



	@ConfigurationProperties(prefix = "spring.datasource.db1")
	@Bean
	DataSource dataSource() {
		var sql = DataSourceBuilder.create()
				.type(HikariDataSource.class).build();
		return sql;
	};

	@Bean
	AbstractEntityManagerFactoryBean abstractEntityManagerFactoryBean(
			EntityManagerFactoryBuilder builder, DataSource dataSource,
			JpaProperties jpaProperties) {
		return builder.dataSource(dataSource).properties(jpaProperties.getProperties())
				.packages("com.example.dacn.db1").build();
	}

	@Bean
	AbstractPlatformTransactionManager db1TrManager(
			AbstractEntityManagerFactoryBean abstractEntityManagerFactoryBean) {
		var jpa = new JpaTransactionManager(
				Objects.requireNonNull(abstractEntityManagerFactoryBean.getObject()));
		return jpa;
	}

}
