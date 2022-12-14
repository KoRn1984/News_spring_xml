package by.itacademy.jd2.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/", "/base_page", "/registration/**", "/resources/**")
                .permitAll()
	                .anyRequest()
	                .authenticated()
                .and()
	                .formLogin()
	                //.loginPage("/base_page")
	                .loginProcessingUrl("/authenticateTheUser")
	                .failureUrl("/base_page?error")
	                .defaultSuccessUrl("/news_list")
	                .permitAll()
                .and()
	                .logout()
	                .logoutSuccessUrl("/")
	                .permitAll();
    }
}