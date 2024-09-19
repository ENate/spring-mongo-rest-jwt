package com.minejava.springjwt.security;

import com.minejava.springjwt.jwt.JwtHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.minejava.springjwt.service.UserService;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig { 
    
    private final UserService userService;
    private final JwtHelper jwtHelper;

    private final AccessTokenEntryPoint accessTokenEntryPoint;

    
    private AuthenticationManager authenticationManager;

    public WebSecurityConfig(UserService userService,
    		JwtHelper jwtHelper,
    		AccessTokenEntryPoint accessTokenEntryPoint,
    		AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.accessTokenEntryPoint = accessTokenEntryPoint;
        this.authenticationManager = authenticationManager;
    }

    @Bean
    AccessTokenFilter accessTokenFilter() {
        return new AccessTokenFilter(jwtHelper, userService);
    }

    @Bean
     AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userService);
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return authProvider;
    }

    
    
    @Bean
    SecurityFilterChain securityConfig(HttpSecurity http) throws Exception {
    	http
    	    .csrf(csrf -> csrf.disable())
    	    .cors(cors -> cors.disable())
    	    .exceptionHandling(exception -> exception.authenticationEntryPoint(accessTokenEntryPoint))
    	    .authenticationManager(authenticationManager)
    	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	    .authorizeHttpRequests(authorize -> authorize
    	    		.requestMatchers("/api/auth/**").permitAll()
    	    		.anyRequest().authenticated())
    	    .addFilterBefore(accessTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    }

}
