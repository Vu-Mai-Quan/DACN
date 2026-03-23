package com.example.dacn.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.sql.DataSource;
import java.util.Objects;

@Slf4j
@EnableJpaRepositories(
        basePackages = "com.example.dacn.db1",
        entityManagerFactoryRef = "abstractEntityManagerFactoryBean",
        transactionManagerRef = "db1TrManager")
@Configuration
@RequiredArgsConstructor
public class Db1Config {

    @ConfigurationProperties(prefix = "spring.datasource.db1")
    @Bean
    DataSource dataSourceDbOne() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    AbstractEntityManagerFactoryBean abstractEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder, DataSource dataSourceDbOne,
            JpaProperties jpaProperties) {
        return builder.dataSource(dataSourceDbOne)
                .properties(jpaProperties.getProperties())
                .packages("com.example.dacn.db1").build();
    }

    @Bean
    AbstractPlatformTransactionManager db1TrManager(
            AbstractEntityManagerFactoryBean abstractEntityManagerFactoryBean, @Qualifier("flywayDb1") Flyway flyway) {
        flyway.migrate();
        return new JpaTransactionManager(
                Objects.requireNonNull(abstractEntityManagerFactoryBean.getObject()));
    }

    @Bean()
//	@DependsOn("abstractEntityManagerFactoryBean")
    Flyway flywayDb1(DataSource dataSourceDbOne) {

        return Flyway.configure().dataSource(dataSourceDbOne)
                .locations("classpath:db/migration/db1").baselineOnMigrate(true).load();
    }

}
