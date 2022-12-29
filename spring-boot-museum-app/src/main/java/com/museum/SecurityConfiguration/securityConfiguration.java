package com.museum.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.museum.service.UserService;
import com.museum.service.UserServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class securityConfiguration extends WebSecurityConfigurerAdapter{

	
	@Autowired
	private UserService userService;
	
	@Autowired
	 PasswordEncoder passwordEncoder;
	
	@Bean
	UserService userService() {
		return new UserServiceImpl();
	}
    

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    	auth.setUserDetailsService(userService());
    	auth.setPasswordEncoder(passwordEncoder);
    	return auth;
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	auth.userDetailsService(userService);
    	auth.authenticationProvider(authenticationProvider());
    }
    
	@Override
	protected void configure(HttpSecurity http) throws Exception{
			http.
			csrf().disable().
			authorizeHttpRequests()
						.antMatchers("/", "/app-assets/**","/assets/**","/src/**","/forgot/**","/sendOtp/**","/verify_Otp/**","/change_password/**").permitAll()
						.anyRequest().authenticated()
						.and()
						.formLogin(
								 form -> form
	                                .loginPage("/")
	                                .loginProcessingUrl("/login")
	                                .defaultSuccessUrl("/dashBoard")
	                                .permitAll()
								)
						
						.logout(  logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll());
						
	}
	
	
	
}
