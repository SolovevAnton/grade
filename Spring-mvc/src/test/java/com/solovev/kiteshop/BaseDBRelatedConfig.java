package com.solovev.kiteshop;

import com.solovev.kiteshop.config.AuditingConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never") //not to load initial file
@Import(AuditingConfig.class)
@DirtiesContext
public class BaseDBRelatedConfig {
}
