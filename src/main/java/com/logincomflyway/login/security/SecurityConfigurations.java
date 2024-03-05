package com.logincomflyway.login.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static 
org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	SecurityFilter securityFilter;
	
	@Autowired
	private Environment env;
	

	//public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		//'if' para liberar o endpoint do banco H2
				if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
					httpSecurity.headers(headers->{
						headers.frameOptions(frameOp->{
							frameOp.disable();
						});
					});
				}
				
				
		
		return httpSecurity
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(csrf -> {
					csrf
					.ignoringRequestMatchers(toH2Console())
					//.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
					.disable();
				})
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))				
				.authorizeHttpRequests(auth->{
					auth
					.requestMatchers("/favicon.ico").permitAll()		
					.requestMatchers(toH2Console()).permitAll()
					.requestMatchers(HttpMethod.POST,"/myauth/login").permitAll()
					.requestMatchers(HttpMethod.POST,"/myauth/register").permitAll()
					.requestMatchers(HttpMethod.POST,"/products").hasRole("ADMIN")
					.anyRequest().authenticated();
				})
//				.headers(h->{					
//					h.frameOptions(f->{
//						f.disable();
//					});
//				})
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfig = new CorsConfiguration();
		corsConfig.setAllowedOrigins(Arrays.asList("*"));
		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "PATCH"));
		corsConfig.setAllowCredentials(true);
		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfig);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean 
			= new FilterRegistrationBean<>(new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
	
}
