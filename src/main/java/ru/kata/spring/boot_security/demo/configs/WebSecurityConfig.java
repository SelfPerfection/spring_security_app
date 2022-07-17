package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;
    private final UserDetailsServiceImp userDetailsServiceImp;
    private final Environment env;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserDetailsServiceImp userDetailsServiceImp,
                             Environment environment) {
        this.successUserHandler = successUserHandler;
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.env = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                // Access all users
                .antMatchers("/", "/create_users", "/templates/" + env.getProperty("site.template") + "/**",
                                        "/templates/" + env.getProperty("site.template") + "/css/**",
                                        "/templates" + env.getProperty("site.template") + "js/**",
                                        "/images/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("email")
                .successHandler(successUserHandler)
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
            .and()
                .csrf().disable();
    }

    @Bean
    @Description("DaoAuthenticationProvider is an AuthenticationProvider implementation that leverages a UserDetailsService and PasswordEncoder to authenticate a username and password.")
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceImp);
        return daoAuthenticationProvider;
    }

    @Bean
    @Description("Used for encoding passwords")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* In memory auth
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build());
        userDetailsList.add(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build());


        return new InMemoryUserDetailsManager(userDetailsList);
    }
    */
}
