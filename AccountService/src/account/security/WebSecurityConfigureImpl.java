package account.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfigureImpl extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Testing system requirement
                .antMatchers("/actuator/shutdown").permitAll()
                // Sign up via "POST api/auth/signup" available to everyone
                .antMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                .antMatchers(HttpMethod.POST, "api/auth/changepass").authenticated()
                .antMatchers(HttpMethod.GET, "api/empl/payment").hasAnyRole("USER", "ACCOUNTANT")
                .antMatchers(HttpMethod.POST, "api/acct/payments").hasAnyRole("ACCOUNTANT")
                .antMatchers(HttpMethod.PUT, "api/acct/payments").hasAnyRole("ACCOUNTANT")
                .antMatchers(HttpMethod.GET, "api/admin/user").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.DELETE, "api/admin/user").hasRole("ADMINISTRATOR")
                .antMatchers(HttpMethod.PUT, "api/admin/user/role").hasRole("ADMINISTRATOR")
                .anyRequest().denyAll()
                .and()
                .csrf().disable()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    public WebSecurityConfigureImpl(@Autowired UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private final UserDetailsService userDetailsService;
}
