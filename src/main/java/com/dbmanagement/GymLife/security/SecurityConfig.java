package com.dbmanagement.GymLife.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.dbmanagement.GymLife.service.UserService;

@Configuration
public class SecurityConfig {

    // @Bean
    // public InMemoryUserDetailsManager userDetailsManager() {

    // UserDetails admin = User.builder().username("admin")
    // .password("{noop}admin")
    // .roles("MANAGER", "OWNER")
    // .build();

    // return new InMemoryUserDetailsManager(admin);
    // }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); // set the custom user details service
        auth.setPasswordEncoder(passwordEncoder());

        // (REQUIRED) to let the provider check username and password first
        // before throwing DisabledException
        // Pre-authentication checks (username and password)
        auth.setPreAuthenticationChecks(userDetails -> {
        });

        // Post-authentication checks (enaled status of userDetails which is active
        // status of member)
        auth.setPostAuthenticationChecks(userDetails -> {
            if (!userDetails.isEnabled()) {
                throw new DisabledException("This user is not active.");
            }
        });
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(configurer -> configurer
                // .requestMatchers("/").hasAnyRole("MANAGER", "OWNER", "EMPLOYEE", "TRAINER",
                // "GYMMER")
                .requestMatchers("/").permitAll()
                .requestMatchers("/members/**").hasAnyRole("OWNER", "MANAGER", "TRAINER", "EMPLOYEE")
                // .anyRequest().authenticated())
                .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .failureHandler(null)
                        .permitAll())
                .logout(logout -> logout.permitAll().logoutSuccessHandler(new CustomLogoutSuccessHandler()))
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
        // .exceptionHandling((exceptionHandling) -> exceptionHandling
        // .accessDeniedPage("/access-denied"));

        http.httpBasic(Customizer.withDefaults());

        // http.csrf().disable();

        return http.build();
    }
}
