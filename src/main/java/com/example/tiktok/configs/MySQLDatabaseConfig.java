package com.example.tiktok.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

//Now connect with mysql using JPA
/*
docker run -d --rm --name mysql-spring-boot-tutorial \
-e MYSQL_ROOT_PASSWORD=Abc@123456 \
-e MYSQL_USER=lilo4801 \
-e MYSQL_PASSWORD=Abc@123456 \
-e MYSQL_DATABASE=Tiktok \
-p 3309:3306 \
--volume mysql-spring-boot-tutorial-volume:/var/lib/mysql \
mysql:latest

docker run -d --rm --name mysql-spring-boot-tutorial -e MYSQL_ROOT_PASSWORD=Abc@123456 -e MYSQL_USER=lilo4801 -e MYSQL_PASSWORD=Abc@123456 -e MYSQL_DATABASE=Tiktok -p 3309:3306 --volume mysql-spring-boot-tutorial-volume:/var/lib/mysql mysql:latest

mysql -h localhost -P 3309 --protocol=tcp -u lilo4801 -p

* */
@Configuration
@Profile("mysql")
@EnableJpaRepositories(basePackages = "com.example.tiktok.repositories",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager")
public class MySQLDatabaseConfig {

    @Value("${spring.db1.datasource.url}")
    private String url;

    @Value("${spring.db1.datasource.username}")
    private String username;

    @Value("${spring.db1.datasource.password}")
    private String password;

    @Value("${spring.db1.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    // Other configuration beans for MySQL
}
