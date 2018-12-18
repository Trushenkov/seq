package com.example.sequrity.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select login, password, is_active from application.users where login=?")
                .authoritiesByUsernameQuery(
                        "select u.login, r.name " +
                                "from application.users as u " +
                                "left join application.groups_members as gm on gm.users = u.id and gm.deleted = false " +
                                "left join application.groups_roles as gr on gr.groups = (select template from application.groups where id = gm.user_group) and gr.deleted = false " +
                                "left join application.roles as r on r.id = gr.roles and r.deleted  = false " +
                                "where u.deleted = false and login=?"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/home").authenticated();

        http.formLogin().loginPage("/login").defaultSuccessUrl("/home").failureUrl("/incorrect");

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.httpBasic();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
////                .withUser(this.userLogin)
////                .password(this.userPassword)
////                .roles("USER")
////                .and()
//                .withUser("admin")
//                .password("{noop}admin")
//                .roles("ADMIN");
//    }
//

}
