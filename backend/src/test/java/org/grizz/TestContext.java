package org.grizz;

import org.grizz.config.security.oauth2.OAuth2AuthorizationConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FongoContext.class, OgameCloneApplication.class, OAuth2AuthorizationConfig.class})
public class TestContext {
}
