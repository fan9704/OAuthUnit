package com.ps.security;

import com.ps.service.ScopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * Created by samchu on 2017/2/15.
 */

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsAuthenticationProvider customUserDetailsAuthenticationProvider;
    @Autowired
    private CustomJdbcClientDetailsService customJdbcClientDetailsService;
    @Autowired
    private ScopService scopService;
    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info(">> WebSecurityConfiguration.configure AuthenticationManagerBuilder={}", auth);
        auth.authenticationProvider(customUserDetailsAuthenticationProvider);
        log.info("<< WebSecurityConfiguration.configure AuthenticationManagerBuilder");
    }
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/swagger-ui/**",
            //Create User
//            "/**"
            //OAuth
            "/oauth/**",
            //System
            "/error"

    };
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }

    @Bean
    public TokenStore tokenStore() {
        //JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        return new CustomTokenStore();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        final JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // ????????????????????????
        jwtAccessTokenConverter.setSigningKey(resourceServerProperties.getJwt().getKeyValue());
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
        // jwtAccessTokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
        return jwtAccessTokenConverter;
    }

    @Bean
    public CustomTokenServices getDefaultTokenServices() throws Exception {
        CustomTokenServices tokenServices = new CustomTokenServices();
        tokenServices.setAuthenticationManager(authenticationManagerBean());
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setAccessTokenEnhancer(jwtAccessTokenConverter());
        tokenServices.setClientDetailsService(customJdbcClientDetailsService);
        tokenServices.setScopService(scopService);
        tokenServices.setSupportRefreshToken(true);
        return tokenServices;
    }
}