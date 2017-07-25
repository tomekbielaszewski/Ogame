package org.grizz.config.docs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.any;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private String securitySchemaOAuth2 = "facebook";
  private String authorizationScopeGlobal = "global";
  private String authorizationScopeGlobalDesc = "accessEverything";

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(any())
        .build()
        .securitySchemes(newArrayList(securitySchema()))
        .securityContexts(newArrayList(securityContext()));
  }

  private SecurityScheme securitySchema() {
    AuthorizationScope authorizationScope = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
    LoginEndpoint loginEndpoint = new LoginEndpoint("http://localhost:8080/login/facebook");
    GrantType grantType = new ImplicitGrant(loginEndpoint, "access_token");
    return new OAuth(securitySchemaOAuth2, newArrayList(authorizationScope), newArrayList(grantType));
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .forPaths(any())
        .securityReferences(defaultAuth())
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope(authorizationScopeGlobal, authorizationScopeGlobalDesc);
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return newArrayList(
        new SecurityReference(securitySchemaOAuth2, authorizationScopes));
  }
}