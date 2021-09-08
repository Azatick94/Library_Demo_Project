package com.luxoft.library.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    public SecurityConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // by_author has higher priority than book/**
                .antMatchers(HttpMethod.GET, "/books/by_author/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/books/**", "/authors/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/books/**", "/authors/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/books/**", "/authors/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/books/**", "/authors/**").hasRole("ADMIN")
                .and().httpBasic()
                .and().logout()
                .and().csrf().disable();
//         csrf disable gives access to post/delete operations
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        if (profiles.contains("security_2nd_approach")) {
            auth.userDetailsService(users());
            System.out.println("SECOND APPROACH");
        } else {
            auth.inMemoryAuthentication()
                    .withUser("user").password(passwordEncoder().encode("user")).roles("USER")
                    .and()
                    .withUser("admin").password(passwordEncoder().encode("admin")).roles("USER", "ADMIN");
            System.out.println("FIRST APPROACH");
        }
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile(value = "security_2nd_approach")
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
