package com.example.tiktok.configs;

import com.example.tiktok.entities.Role;
import com.example.tiktok.entities.User;
import com.example.tiktok.exceptions.CreateFailureException;
import com.example.tiktok.repositories.RoleRepository;
import com.example.tiktok.repositories.UserRepository;
import com.example.tiktok.utils.LanguageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@Profile("h2")
@EnableJpaRepositories(basePackages = "com.example.tiktok.repositories",
        entityManagerFactoryRef = "h2EntityManagerFactory",
        transactionManagerRef = "h2TransactionManager")
public class H2DatabaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(H2DatabaseConfig.class);

    @Value("${spring.db2.datasource.url}")
    private String url;

    @Value("${spring.db2.datasource.username}")
    private String username;

    @Value("${spring.db2.datasource.password}")
    private String password;
    @Value("${spring.db2.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    @Primary
    public DataSource h2DataSource() {
        logger.info("connect to h2 database");
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }


    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, RoleRepository roleRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Role admin = new Role("ROLE_ADMIN");
                Role editor = new Role("ROLE_EDITOR");
                Role customer = new Role("ROLE_CUSTOMER");
                roleRepository.save(admin);
                roleRepository.save(editor);
                roleRepository.save(customer);

                List<Role> foundRoles = roleRepository.findAll();
                Set<Role> addRoles = new HashSet<>();
                addRoles.addAll(foundRoles);
                User user = User.builder()
                        .username("test")
                        .nickname("test")
                        .email("test@test.com")
                        .password("123456")
                        .roles(addRoles)
                        .build();
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String password = passwordEncoder.encode(user.getPassword());
                user.setPassword(password);
                userRepository.save(user);


            }
        };
    }
    // Other configuration beans for H2
}
