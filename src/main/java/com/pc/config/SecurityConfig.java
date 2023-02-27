package com.pc.config;

import com.pc.service.LoginServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Require all requests to be authenticated except the one listed below
        http.authorizeRequests().antMatchers("/javax.faces.resource/**", "/resources/img/**",
                "/register.xhtml/**", "/resources/barcelona-layout/**",
                "/home.xhtml/**", "/reporttodhet.xhtml/**",
                "/newsletter.xhtml/**", "/newsandevents.xhtml/**", "/newsdetails.xhtml/**",
                "/rest/**", "/api/**", "/forgotpassword.xhtml/**",
                "/changepassword.xhtml/**", "/institutionreportstatus.xhtml/**", "/contactus.xhtml/**").permitAll()
                .antMatchers("/admin/**")
                .hasRole("ADMIN").anyRequest().authenticated()
                .antMatchers("/user/**")
                .hasRole("USER").anyRequest().authenticated();

        // login
        http.formLogin().loginPage("/login.xhtml").permitAll().
                failureUrl("/login.xhtml?error=true").defaultSuccessUrl("/dashboard.xhtml", true);

        // logout
        http.logout().logoutSuccessUrl("/home.xhtml");


        // not needed as JSF 2.2 is implicitly protected against CSRF
        http.csrf().disable();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        return new LoginServiceImp();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


}
