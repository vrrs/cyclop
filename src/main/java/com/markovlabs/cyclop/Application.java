package com.markovlabs.cyclop;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.uuid.EthernetAddress;
import com.netflix.config.DynamicPropertyFactory;

import javaslang.control.Try;

@SpringBootApplication
@PropertySources({@PropertySource(value = "classpath:/config/application.properties")})
public class Application {
    
    private static final Logger log = LoggerFactory.getLogger(Application.class);
    
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(getJSONInHTTPToPOJOMapper());
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter getJSONInHTTPToPOJOMapper() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.registerModule(new JSR310Module());
        mapper.setDateFormat(new SimpleDateFormat("YYYY-MM-DD"));
        converter.setObjectMapper(mapper);
        return converter;
    }
    
    @Bean
    public DSLContext getMySQLJOOQContext() {
        return DSL.using(getDataSource(), SQLDialect.MYSQL);
    }
    
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        return newDataSource();
    }    
    
    private DataSource newDataSource() {
        DynamicPropertyFactory properties = getProperties();
        String dbURL = properties.getStringProperty("mysql.url", "jdbc:mysql://localhost:3306/cyclopdb").get();
        String dbUser = properties.getStringProperty("mysql.user", "root").get();
        String dbPassword = properties.getStringProperty("mysql.password", "").get();
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(dbURL);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        log.info("BasicDataSource bean for MySQL has been created with user " + dbUser + ", pwd " + dbPassword + ", url " + dbURL);
        return dataSource;
    }
    
    @Bean
    public DynamicPropertyFactory getProperties() {
        DynamicPropertyFactory properties = DynamicPropertyFactory.getInstance();
        properties.getStringProperty("logLevel", "INFO", () -> 
            LogManager.getRootLogger()
                      .setLevel(Level.toLevel(properties
                                      .getStringProperty("logLevel", "INFO").get())));
        return properties;
    }
    
    @Bean
    public EthernetAddress getEthernetAddress() {
        return Try.of(EthernetAddress::fromInterface)
                  .orElse(EthernetAddress.constructMulticastAddress());
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}