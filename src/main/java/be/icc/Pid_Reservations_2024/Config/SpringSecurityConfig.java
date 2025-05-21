package be.icc.Pid_Reservations_2024.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12);
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable())
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.requestMatchers("/save-troupe").hasRole("ADMIN");
                    auth.requestMatchers("/user").hasRole("MEMBER");
                    auth.requestMatchers("/reservation/**").hasAnyRole("ADMIN", "MEMBER");
                    auth.requestMatchers("/representationReservation").hasAnyRole("ADMIN", "MEMBER");
                    auth.requestMatchers("/modification/**").hasAnyRole("ADMIN", "MEMBER");
                    // This is for API
                    auth.requestMatchers("/api/**").permitAll(); // access for everyone
                    auth.requestMatchers("/api/admin/**").hasRole("ADMIN"); // access only for Admin
                    auth.anyRequest().permitAll();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("login")
                        .failureUrl("/login?loginError=true"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logoutSuccess=true")
                        .deleteCookies("JSESSIONID"))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login?loginRequired=true")))
                .build();
    }


}
