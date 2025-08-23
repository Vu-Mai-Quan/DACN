/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author ADMIN
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.dacn.db1",
        entityManagerFactoryRef = "db1EntityManagerFactory",
        transactionManagerRef = "db1TransactionManager"
)
public class DbConfig1 extends ADbConfig {

    {
        this.props.put(Environment.HBM2DDL_AUTO, "none");
        this.props.put(Environment.STATEMENT_BATCH_SIZE, "20");
        this.props.put(Environment.ORDER_INSERTS, "true");
        this.props.put(Environment.ORDER_UPDATES, "true");
        this.props.put(Environment.USE_SQL_COMMENTS, "true");
        this.props.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
//        this.props.put(Environment.DIALECT, "org.hibernate.community.dialect.SQLiteDialect");

    }

    @Bean(name = "db1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    @Override
    protected DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties();
    }


    @Bean(name = "db1DataSource")
    protected DataSource db1DataSource(@Qualifier("db1DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Override
    @Bean(name = "db1EntityManagerFactory")
    protected LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean(@Qualifier("db1DataSource") DataSource dataSource) {
        var r = super.containerEntityManagerFactoryBean(dataSource);
        r.setPackagesToScan("com.example.dacn.db1");
        r.setJpaPropertyMap(this.props);
        return r;
    }

    @Override
    @Bean(name = "db1TransactionManager")
    protected PlatformTransactionManager dbTransactionManager(@Qualifier("db1EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return super.dbTransactionManager(entityManagerFactory); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

}
