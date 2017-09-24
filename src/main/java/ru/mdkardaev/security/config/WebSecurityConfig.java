package ru.mdkardaev.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.mdkardaev.security.RestAuthenticationEntryPoint;
import ru.mdkardaev.security.SkipPathRequestMatcher;
import ru.mdkardaev.security.TokenAuthenticationProcessingFilter;
import ru.mdkardaev.security.TokenAuthenticationProvider;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String PROTECTED_ENTRY_POINT = "/api/**";
    private static final String[] PUBLIC_ENTRY_POINT = {"/register", "/signin"};

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private TokenAuthenticationProvider tokenAuthenticationProvider;
    @Autowired
    private AuthenticationManager authenticationManager;


    private TokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() {
        SkipPathRequestMatcher matcher =
                new SkipPathRequestMatcher(Arrays.asList(PUBLIC_ENTRY_POINT), PROTECTED_ENTRY_POINT);

        TokenAuthenticationProcessingFilter filter
                = new TokenAuthenticationProcessingFilter(authenticationFailureHandler, matcher);
        filter.setAuthenticationManager(authenticationManager);

        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(tokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_ENTRY_POINT).permitAll() // Login end-point
                .and()
                .authorizeRequests()
                .antMatchers(PROTECTED_ENTRY_POINT).authenticated() // Protected API End-points
                .and()
                .addFilterAt(buildJwtTokenAuthenticationProcessingFilter(), BasicAuthenticationFilter.class);
    }
}
