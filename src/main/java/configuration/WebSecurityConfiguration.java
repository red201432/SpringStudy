package configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import security.JWTAuthenticationFilter;
import security.JwtAuthenticationTokenFilter;
import service.MyAuthenticationProvider;
import service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	 
	 @Autowired
	 private AuthSuccessHandler authSuccessHandler;
	 
	 @Autowired
	 MyAuthenticationProvider authenticationProvider;
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		 http.csrf().disable();
		 http.authorizeRequests()
		 	.antMatchers(HttpMethod.OPTIONS).permitAll()
		 	.antMatchers("/index").authenticated()
			.and()
			.formLogin().loginPage("/login").successHandler(authSuccessHandler)
			.permitAll();
//			.and()
//			.exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//			.and()
//			.addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	 @Autowired
	 protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		// TODO Auto-generated method stub
//		 	builder.userDetailsService(userService);
		 builder.authenticationProvider(authenticationProvider);
		}
}
