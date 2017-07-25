package org.grizz.config.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
public class OAuth2AuthorizationConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private OAuth2ClientContext oauth2ClientContext;

  @Autowired
  private ApplicationEventPublisher eventPublisher;

  @Bean
  @ConfigurationProperties("facebook.client")
  public AuthorizationCodeResourceDetails facebook() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("facebook.resource")
  public ResourceServerProperties facebookResource() {
    return new ResourceServerProperties();
  }

  @Bean
  @ConfigurationProperties("github.client")
  public AuthorizationCodeResourceDetails github() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("github.resource")
  public ResourceServerProperties githubResource() {
    return new ResourceServerProperties();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/login**", "/webjars/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and().logout().logoutSuccessUrl("/").permitAll()
        .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and().addFilterBefore(facebookOAuth2Filter(), BasicAuthenticationFilter.class);
  }

  private Filter facebookOAuth2Filter() {
    return createOAuth2Filter("/login/facebook", facebook(), oauth2ClientContext, facebookResource(), eventPublisher);
  }

  private Filter createOAuth2Filter(String url,
                                    AuthorizationCodeResourceDetails resourceDetails,
                                    OAuth2ClientContext oAuth2ClientContext,
                                    ResourceServerProperties resourceServerProperties,
                                    ApplicationEventPublisher eventPublisher) {
    OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(url);
    OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails, oAuth2ClientContext);
    filter.setRestTemplate(oAuth2RestTemplate);
    UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceDetails.getClientId());
    tokenServices.setRestTemplate(oAuth2RestTemplate);
    filter.setTokenServices(tokenServices);
    filter.setApplicationEventPublisher(eventPublisher);
    return filter;
  }
}
