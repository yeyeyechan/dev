package com.multi.oauth20authserver;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  DataSource dataSource;
  @Autowired
  BCryptPasswordEncoder passwordEncoder;

//public TokenStore tokenStore() {
//return new JdbcTokenStore(dataSource);
//}
public JwtAccessTokenConverter accessTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("dfhtrerterwefewfewf");
    return converter;
  }

  public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
  }
  public ApprovalStore approvalStore() {
    //return new InMemoryApprovalStore();
   return new JdbcApprovalStore(dataSource);
  }
    
  public AuthorizationCodeServices authorizationCodeServices() {
//    return new InMemoryAuthorizationCodeServices();
    return new JdbcAuthorizationCodeServices(dataSource);
  }

// @Override
//  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//    endpoints
//      .tokenStore(tokenStore())
//      .approvalStore(approvalStore())
//      .authorizationCodeServices(authorizationCodeServices());
//  }
  @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients();
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("client1").secret(passwordEncoder.encode("1234"))
            .authorizedGrantTypes("authorization_code","implicit","password",
                 "client_credentials", "refresh_token")
            .scopes("contacts", "profiles", "messages")
            .authorities("TEST_CLIENT")
            .redirectUris("http://jcornor.com:8080/callback", "http://localhost:8080/callback")
            .accessTokenValiditySeconds(3600)
            .refreshTokenValiditySeconds(0);
    }   
    
    @Override
    public void configure (AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints
        .tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter())
        .approvalStore(approvalStore())
        .authorizationCodeServices(authorizationCodeServices());
    }
}