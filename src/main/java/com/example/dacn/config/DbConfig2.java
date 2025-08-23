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
 *
 * @author ADMIN
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.dacn.db2",
        entityManagerFactoryRef = "db2EntityManagerFactory",
        transactionManagerRef = "db2TransactionManager"
)
public class DbConfig2 extends ADbConfig {

    

    {
        this.props.put(Environment.HBM2DDL_AUTO, "update");
        this.props.put(Environment.DIALECT, "org.hibernate.community.dialect.SQLiteDialect");

    }

    @Override
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    @Bean(name = "DataSourcePropertiesSqlite")
    protected DataSourceProperties dataSourceProperties() {
        return super.dataSourceProperties(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Bean(name = "db2DataSource")
    protected DataSource db2DataSource(@Qualifier("DataSourcePropertiesSqlite") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Override
    @Bean(name = "db2EntityManagerFactory")
    protected LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean(@Qualifier("db2DataSource") DataSource dataSource) {
        var r = super.containerEntityManagerFactoryBean(dataSource);
        r.setPackagesToScan("com.example.dacn.db2");
        r.setJpaPropertyMap(this.props);
        return r;
    }

    @Override
    @Bean(name = "db2TransactionManager")
    protected PlatformTransactionManager dbTransactionManager(@Qualifier("db2EntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return super.dbTransactionManager(entityManagerFactory); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
}
