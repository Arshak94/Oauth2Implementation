package com.example.user.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;

import javax.sql.DataSource;

@Configuration
@EnableSocial
public class AppConfig  extends SocialAutoConfigurerAdapter{
    /*@Bean
    public SpringLiquibase liquibase(DataSource dataSource)  {
        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db.changelog.xml");

        return liquibase;
    }*/
    @Value("${spring.social.facebook.appId}")
    private String facebookappId;

    @Value("${spring.social.facebook.appSecret}")
    private String facebookappSecret;

    @Value("${spring.social.google.appId}")
    private String googleappId;

    @Value("${spring.social.google.appSecret}")
    private String googleappSecret;

    @Primary
    @Bean
    public FacebookConnectionFactory facebookConnectionFactory(){
        return new FacebookConnectionFactory(facebookappId, facebookappSecret);
    }


    @Bean
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        return new GoogleConnectionFactory(googleappId, googleappSecret);
    }

    /*@Bean
    public JavaMailSender meilSender(){
        return new JavaMailSenderImpl();
    }*/
}
