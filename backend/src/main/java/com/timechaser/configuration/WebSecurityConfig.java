package com.timechaser.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.timechaser.repository.UserRepository;
import com.timechaser.security.MyUserDetails;
import com.timechaser.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private final UserRepository userRepo;

    public WebSecurityConfig(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

	// @Override
    // protected void configure(HttpSecurity http) throws Exception {
	// 	http.httpBasic().disable() //Disable log in page
	// 		.authorizeRequests(auth -> auth.anyRequest().permitAll())
	// 		.csrf().disable();
	// 
	// 	http.headers().frameOptions().disable();
    // }

    // TODO: Implement HTTP configuration
    // Replace the function above when ready to protect routes
    // Code left here was for testing original security implementation
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
            .antMatchers("/role/**").authenticated()
            .antMatchers("/h2-console").permitAll();
        http.csrf().disable();
	    http.headers().frameOptions().disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
     
    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {        
    //     auth.userDetailsService(username -> userRepo
    //     .findByUsername(username).map(MyUserDetails::new)
    //     .orElseThrow(
    //         () -> new UsernameNotFoundException(
    //             String.format("User: %s, not found", username)
    //         )
    //     ));
    // }

}
