package org.grizz;

import org.grizz.config.security.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FongoContext.class, OgameCloneApplication.class, SecurityConfig.class})
public class TestContext {
}
