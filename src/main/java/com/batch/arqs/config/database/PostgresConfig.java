package com.batch.arqs.config.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PostgresConfig {

    @Value("${config.postgres.url}")
    private String url;

    @Value("${config.postgres.userName}")
    private String userName;

    @Value("${config.postgres.passord}")
    private String userPassword;

    @Value("${config.postgres.driverClassName}")
    private String driverClassName;

    @Value("${config.postgres.maximumPoolSize}")
    private Integer maxPoolSize;

    @Value("${config.postgres.maxLifeTime}")
    private Integer maxLifeTime;

    @Value("${config.postgres.connectionTimeOut}")
    private Integer connectionTimeout;

    private static final Logger LOGGER = LogManager.getLogger(PostgresConfig.class);

    @Bean
    public DataSource dataSource() {
        LOGGER.info("---> Iniciando a conexão com o banco de dados PostGres ...");
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(userName);
        hikariConfig.setPassword(userPassword);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        hikariConfig.setMaxLifetime(maxLifeTime);
        hikariConfig.setConnectionTimeout(connectionTimeout);
        LOGGER.info("---> Finalizando a conexão com o banco de dados PostGres ...");
        return new HikariDataSource(hikariConfig);
    }
}
