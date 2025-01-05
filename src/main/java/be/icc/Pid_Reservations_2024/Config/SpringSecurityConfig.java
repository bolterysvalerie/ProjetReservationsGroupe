package be.icc.Pid_Reservations_2024.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class to set up custom security settings for the application.
 *
 * This configuration enables web security, defines URL authorization rules, and configures
 * authentication mechanisms, including form login, password encoding, and "remember me" functionality.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    /**
     * Configures the HTTP security settings, including URL-based access control.
     *
     * @param httpSecurity the {@link HttpSecurity} object to customize
     * @return a {@link SecurityFilterChain} bean with the configured security rules
     * @throws Exception if an error occurs during security configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll();
                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.requestMatchers("/user").hasRole("USER");
                    auth.anyRequest().authenticated();
                }).formLogin(Customizer.withDefaults())
                .rememberMe(Customizer.withDefaults()).build();

    }

    /**
     * Configures the {@link AuthenticationManager} with a custom {@link UserDetailsService}
     * and a {@link BCryptPasswordEncoder} for password encryption.
     *
     * @param httpSecurity the {@link HttpSecurity} object
     * @param bCryptPasswordEncoder the {@link BCryptPasswordEncoder} for password encoding
     * @return an {@link AuthenticationManager} bean
     * @throws Exception if an error occurs during authentication manager setup
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authManagerBuild = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authManagerBuild.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);

        return authManagerBuild.build();
    }

    /**
     * Provides a {@link BCryptPasswordEncoder} bean for password hashing.
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}