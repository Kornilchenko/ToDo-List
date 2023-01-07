/*
package com.example.hsqldb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //Аннотация — это то, что позволяет  делать  @PreAuthorize аннотации.
public class WebSecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails userAdmin = User
                .withUsername("admin")
                .password(getEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails user = User
                .withUsername("user")
                .password(getEncoder().encode("user"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user, userAdmin);
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeRequests(authorize -> authorize
                        .mvcMatchers("/task").hasAnyAuthority("write"))
                .authorizeRequests()
                .mvcMatchers("/task/**")
                .hasRole("ADMIN")
                .and()
                .authorizeRequests()
                .mvcMatchers("task/{id}", "/task/all")
                .hasRole("USER")
                .and()
                .authorizeRequests().anyRequest().authenticated().and()
                .formLogin();
        return http.build();
    }

   */
/* protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password(getEncoder().encode("user")).roles("USER")
                .and()
                .withUser("admin").password(getEncoder().encode("admin")).roles("ADMIN")
                .and()
                .passwordEncoder(getEncoder());
    }*//*


*/
/*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/task/all").permitAll()
                //.mvcMatchers("/task/all").hasRole("ADMIN")
                .mvcMatchers("task/{id}").hasAnyRole("ADMIN", "USER")
                //.mvcMatchers("/").permitAll() //для всех
                .mvcMatchers("/**").authenticated() //доступ для авторизованых пользователей
                .and().formLogin();
    }*//*


   */
/* @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/task/all").permitAll() //разешить всем
                .anyRequest().authenticated()
                .and().formLogin();
    }*//*


}
*/
