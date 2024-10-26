package com.blog.config;

import com.blog.Security.CustomUserDetailsService;
import com.blog.Security.JWTAuthenticationFilter;
import com.blog.Security.JWTauthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;

import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig{
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JWTauthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    public static final String[] PUBLIC_URLS = {"/api/v1/auth/**", "/v3/api-docs", "/v2/api-docs",
            "/swagger-resources/**", "/swagger-ui/**", "/webjars/**"

    };


//    protected void configure(HttpSecurity http) throws Exception{
//
//        http.
//                csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/auth/login")
//                .permitAll()
//                .requestMatchers(HttpMethod.GET)
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().exceptionHandling()
//                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {




        http.
                csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_URLS)
                .permitAll()
                .requestMatchers(HttpMethod.GET)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;


    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(this.customUserDetailsService).passwordEncoder(passwordEncoder());

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

//    @Bean
//
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.();
//    }

     @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}