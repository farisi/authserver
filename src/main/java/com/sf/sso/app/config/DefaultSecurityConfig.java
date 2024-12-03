package com.sf.sso.app.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import com.sf.sso.app.services.UserDetailConcret;

import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
public class DefaultSecurityConfig {
	
	@Resource
	UserDetailConcret usrDetail;
	
	
	@Bean
    DaoAuthenticationProvider authProvider() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usrDetail);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }
	
	@Bean
	@Order(1)
	SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer serverConfigure = OAuth2AuthorizationServerConfigurer.authorizationServer();
		http
		.securityMatcher(serverConfigure.getEndpointsMatcher())
		.with(serverConfigure, (authorizationServer) ->
			authorizationServer
				.oidc(withDefaults())	// Enable OpenID Connect 1.0
		)
		// Redirect to the login page when not authenticated from the
		// authorization endpoint
		.exceptionHandling((exceptions) -> exceptions
			.defaultAuthenticationEntryPointFor(
				new LoginUrlAuthenticationEntryPoint("/login"),
				new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
			)
		);
		return http.build();
	}

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
        		authorizeRequests -> authorizeRequests
        		.anyRequest()
                .authenticated())
            .formLogin(withDefaults());
        return http.build();
    }

}