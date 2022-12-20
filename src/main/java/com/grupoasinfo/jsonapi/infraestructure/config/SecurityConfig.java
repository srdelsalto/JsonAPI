package com.grupoasinfo.jsonapi.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        String moduleName = "/general/**";
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        moduleName,"/swagger-ui.html#/**")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS,
                        moduleName)
                .permitAll()
                .antMatchers(HttpMethod.POST,
                        moduleName,"/swagger-ui.html#/**")
                .permitAll()
                .antMatchers(HttpMethod.PUT,
                        moduleName,"/swagger-ui.html#/**")
                .permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers().contentSecurityPolicy("script-src 'self'");

        return http.build();
    }

}
