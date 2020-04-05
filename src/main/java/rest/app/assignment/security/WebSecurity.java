package rest.app.assignment.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import rest.app.assignment.persistence.repositories.UserRepository;
import rest.app.assignment.service.UserService;


@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final UserRepository userRepository;

    public WebSecurity(UserService userDetailsService,
    		BCryptPasswordEncoder bCryptPasswordEncoder,
    		UserRepository userRepository
    		) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
			http
	        .cors().and()
	        .csrf().disable().authorizeRequests()
	        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
	        .permitAll()
	        .antMatchers(SecurityConstants.H2_CONSOLE)
	        .permitAll()
	        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
	        .permitAll()
	        //.antMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
	        .antMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("DELETE_AUTHORITY")
	        .anyRequest().authenticated().and()
	        .addFilter(getAuthenticationFilter())
	        .addFilter(new AuthorizationFilter(authenticationManager(),userRepository))
	        .sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			
			//This is done to enable the h2 database console
			http.headers().frameOptions().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	protected AuthenticationFilter getAuthenticationFilter() throws Exception {
		final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
		filter.setFilterProcessesUrl(SecurityConstants.SIGN_UP_URL + "/login");
		return filter;
	}
	
}
