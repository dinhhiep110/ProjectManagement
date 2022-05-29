package com.ntq.projectmanagement.config;

import com.ntq.projectmanagement.exceptionhandling.CustomAccessDeniedHandler;
import com.ntq.projectmanagement.jwt.JwtAuthenticationFilter;
import com.ntq.projectmanagement.jwt.JwtAuthorizationFilter;
import com.ntq.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.concurrent.TimeUnit;

import static com.ntq.projectmanagement.auth.UserAccountRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
                .formLogin() //form based Authentication

                //                .loginPage("/login") // customize login page
                .defaultSuccessUrl("/home",true)
                .permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .rememberMe()// existed in 2 weeks. if no check, it expired about 30 min of no activities
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(10))
                .key("token secured")
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .permitAll()
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID","remember-me","XSRF-TOKEN")
                .and()
                .authorizeRequests()
//                .antMatchers("/","/home").permitAll()
                .antMatchers("/api/employees/**","/employee/**").hasAnyRole(EMPLOYEEMANAGER.name(), ADMIN.name())
                .antMatchers("/project/**","/api/projects/**").hasAnyAuthority("ROLE_"+PROJECTMANAGER.name(),"ROLE_"+ADMIN.name())
//                .antMatchers("/api/employees/**","/employee/**").hasAnyRole(EMPLOYEEMANAGER.name(), ADMIN.name())
//                .antMatchers("/project/**","/api/projects/**").hasAnyRole(PROJECTMANAGER.name(),ADMIN.name())
//                .antMatchers(HttpMethod.POST,"/api/employees/**").hasAuthority(EMPLOYEEMANAGER_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT,"/api/employees/**").hasAuthority(EMPLOYEEMANAGER_WRITE.getPermission())
//                .antMatchers(HttpMethod.DELETE,"/api/employees/**").hasAuthority(EMPLOYEEMANAGER_WRITE.getPermission())
//                .antMatchers("/api/employees/**").hasAnyRole(EMPLOYEEMANAGER.name(), ADMIN.name())
                .anyRequest().authenticated();


//                .and()
//            .httpBasic(); //Basic Authentication
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails quangUser = User
//                .builder()
//                .username("tienquang")
//                .password(passwordEncoder.encode("haha"))
////                .roles(EMPLOYEEMANAGER.name())
//                .authorities(EMPLOYEEMANAGER.getGrantedAuthorities())
//                .build();
//
//        UserDetails duyhiepUser = User.builder()
//                .username("duyhiep")
//                .password(passwordEncoder.encode("12345"))
////                .roles(PROJECTMANAGER.name())
//                .authorities(PROJECTMANAGER.getGrantedAuthorities())
//                .build();
//
//        UserDetails adminUser = User.builder()
//                .username("admin")
//                .password(passwordEncoder.encode("admin"))
////                .roles(ADMIN.name())
//                .authorities(ADMIN.getGrantedAuthorities())
//                .build();
//        return new InMemoryUserDetailsManager(duyhiepUser,quangUser,adminUser);
//    }


}
