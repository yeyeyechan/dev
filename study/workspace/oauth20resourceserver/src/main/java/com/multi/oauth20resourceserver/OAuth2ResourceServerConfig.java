package com.multi.oauth20resourceserver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableResourceServer
@Configuration
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {
  @Autowired
  DataSource dataSource;
  
//  public TokenStore tokenStore() {
//    return new JdbcTokenStore(dataSource);
//  }
  public JwtAccessTokenConverter accessTokenConverter() {
	    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	    converter.setSigningKey("dfhtrerterwefewfewf");
	    return converter;
	  }

	  public TokenStore tokenStore() {
	    return new JwtTokenStore(accessTokenConverter());
	  }
  @Override
  public void configure(HttpSecurity http) throws Exception { 
      http.csrf().disable()
         .requestMatchers()
             .antMatchers("/api/**")
         .and().authorizeRequests()
             .antMatchers("/api/contacts").access("#oauth2.hasScope('contacts')")
             .antMatchers("/api/profiles").access("#oauth2.hasScope('profiles')")
         .and().exceptionHandling()
             .accessDeniedHandler(new OAuth2AccessDeniedHandler());
  } 
}
