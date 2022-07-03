package com.minejava.springjwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.minejava.springjwt.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private AccessTokenEntryPoint accessTokenEntryPoint;

    private final AuthenticationConfiguration configuration;

    
    @Bean
    public AccessTokenFilter accessTokenFilter() {
        return new AccessTokenFilter();
    }

    
     
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
    // @Bean
    // protected AuthenticationManager authenticationManager() throws Exception {
    //     return configuration.getAuthenticationManager();
    // }
    
   
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

 
    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }
    // @Autowired
    // protected void configure(AuthenticationManagerBuilder builder) throws Exception {
    //     builder.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(accessTokenEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(accessTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.cors().and().csrf().disable()
    //         .exceptionHandling().authenticationEntryPoint(accessTokenEntryPoint).and()
    //         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
    //         .authorizeRequests().antMatchers("/api/auth**").permitAll()
    //         .anyRequest().authenticated();
            
    //         http.addFilterBefore(accessTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    //         return http.build();
    // }
}
