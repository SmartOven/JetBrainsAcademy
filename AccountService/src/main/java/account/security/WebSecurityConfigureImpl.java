package account.security;

import account.exception.handler.ApiAccessDeniedHandler;
import account.exception.handler.ApiUnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class WebSecurityConfigureImpl extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles(ADMINISTRATOR)
                .and()
                .passwordEncoder(encoder);
        auth
                .userDetailsService(service)
                .passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/auth/signup", "/actuator/shutdown").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/auth/changepass").authenticated()
                .mvcMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole(USER, ACCOUNTANT)
                .mvcMatchers(HttpMethod.POST, "/api/acct/payments").hasRole(ACCOUNTANT)
                .mvcMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole(ACCOUNTANT)
                .mvcMatchers("/api/admin/**").hasRole(ADMINISTRATOR)
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no session
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler) // handle Access denied errors
                .authenticationEntryPoint(unauthorizedHandler); // handle auth errors
    }

    public WebSecurityConfigureImpl(@Autowired UserDetailsService service,
                                    @Autowired PasswordEncoder encoder,
                                    @Autowired ApiUnauthorizedHandler unauthorizedHandler,
                                    @Autowired ApiAccessDeniedHandler accessDeniedHandler) {
        this.service = service;
        this.encoder = encoder;
        this.unauthorizedHandler = unauthorizedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    private final UserDetailsService service;
    private final PasswordEncoder encoder;
    private final ApiUnauthorizedHandler unauthorizedHandler;
    private final ApiAccessDeniedHandler accessDeniedHandler;
    private static final String USER = "USER";
    private static final String ADMINISTRATOR = "ADMINISTRATOR";
    private static final String ACCOUNTANT = "ACCOUNTANT";
}
