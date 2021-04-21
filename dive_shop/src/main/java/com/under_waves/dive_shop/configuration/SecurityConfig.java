//package com.under_waves.dive_shop.configuration;
//
//import com.andreev.amatsport.security.JwtRequestFilter;
//import com.andreev.amatsport.security.SecurityAuthenticationProvider;
//import com.andreev.amatsport.security.UnauthorisedAuthenticationEntryPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final SecurityAuthenticationProvider securityAuthenticationProvider;
//    private final UnauthorisedAuthenticationEntryPoint unauthorisedAuthenticationEntryPoint;
//    private final JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    public SecurityConfig(SecurityAuthenticationProvider securityAuthenticationProvider, UnauthorisedAuthenticationEntryPoint unauthorisedAuthenticationEntryPoint, JwtRequestFilter jwtRequestFilter) {
//        this.securityAuthenticationProvider = securityAuthenticationProvider;
//        this.unauthorisedAuthenticationEntryPoint = unauthorisedAuthenticationEntryPoint;
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(securityAuthenticationProvider);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////                csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
////                .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*","/login", "/register").permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(unauthorisedAuthenticationEntryPoint)
//                .and()
//                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin().disable()
//                .logout().disable();
//    }
//}
