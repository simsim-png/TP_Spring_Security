package com.example.tp_spring_security.Security;

import com.example.tp_spring_security.Auth.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import static com.example.tp_spring_security.Security.AppUserPermission.COURSE_WRITE;

@EnableWebSecurity
@Configuration
public class AppSecurityConfig {

        private final PasswordEncoder passwordEncoder;
        private final ApplicationUserService applicationUserService;
        @Autowired
        public AppSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
            this.passwordEncoder = passwordEncoder;
            this.applicationUserService = applicationUserService;
        }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> {
            try {
                auth.requestMatchers("/", "index", "/css/*", "/js/*").hasRole(AppUserRole.ADMIN.name());
                auth.requestMatchers(HttpMethod.DELETE,"/management/api/*").hasAuthority(AppUserPermission.COURSE_WRITE.name());
                auth.requestMatchers(HttpMethod.POST,"/management/api/*").hasAuthority(AppUserPermission.COURSE_WRITE.name());
                auth.requestMatchers(HttpMethod.PUT,"/management/api/*").hasAuthority(AppUserPermission.COURSE_WRITE.name());
                auth.requestMatchers(HttpMethod.GET,"/management/api/*").hasAnyRole(AppUserRole.ADMIN.name(), AppUserRole.ADMIN_TRAINEE.name());
                auth.anyRequest().authenticated().and().httpBasic().and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                auth.and().formLogin().loginPage("/login").permitAll();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return http.build();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
            auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider=new
                DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }
}
