package account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests()
                // Testing system requirement
                // Sign up via "POST api/auth/signup" available to everyone
                .mvcMatchers(HttpMethod.POST, "/api/auth/signup", "/actuator/shutdown").permitAll()
                // Add other rules for access authorization
                .mvcMatchers(HttpMethod.POST, "api/auth/changepass").authenticated()
//                .mvcMatchers(HttpMethod.GET, "api/empl/payment").hasAnyRole(USER, ACCOUNTANT)
                .mvcMatchers(HttpMethod.GET, "api/empl/payment").authenticated()
                .mvcMatchers(HttpMethod.POST, "api/acct/payments").hasAnyRole(ACCOUNTANT)
                .mvcMatchers(HttpMethod.PUT, "api/acct/payments").hasAnyRole(ACCOUNTANT)
                .mvcMatchers(HttpMethod.GET, "api/admin/user").hasRole(ADMINISTRATOR)
                .mvcMatchers(HttpMethod.DELETE, "api/admin/user").hasRole(ADMINISTRATOR)
                .mvcMatchers(HttpMethod.PUT, "api/admin/user/role").hasRole(ADMINISTRATOR)
                .mvcMatchers("/api/admin/breached-password").hasRole(ADMINISTRATOR)
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session
    }

    public WebSecurityConfigureImpl(@Autowired UserDetailsService service,
                                    @Autowired PasswordEncoder encoder,
                                    @Autowired RestAuthenticationEntryPoint restAuthenticationEntryPoint) {
        this.service = service;
        this.encoder = encoder;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
    }

    private final UserDetailsService service;
    private final PasswordEncoder encoder;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private static final String USER = "USER";
    private static final String ADMINISTRATOR = "ADMINISTRATOR";
    private static final String ACCOUNTANT = "ACCOUNTANT";
}
