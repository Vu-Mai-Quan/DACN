/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.dacn.config;

import org.hibernate.cfg.Environment;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author ADMIN
 */
public abstract class ADbConfig {

    protected Map<String, String> props = new HashMap<>();

    {
        this.props.put(Environment.SHOW_SQL, "true");
        this.props.put(Environment.FORMAT_SQL, "true");
        this.props.put(Environment.STATEMENT_BATCH_SIZE, "50");
        this.props.put(Environment.ORDER_INSERTS, "true");
        this.props.put(Environment.ORDER_UPDATES, "true");
    }

    protected LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean(
            DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        bean.setJpaVendorAdapter(adapter);
        bean.setDataSource(dataSource);
        return bean;
    }

    protected PlatformTransactionManager dbTransactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory.getObject()));
    }

    protected DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

}
