package org.appsugar.integration.data.jpa.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.test.configuration
 * @className DataJpaTestConfiguration
 * @date 2021-12-18  16:38
 */
@EntityScan(basePackageClasses = TestEntity.class)
@SpringBootApplication
@TestConfiguration
@EnableJpaRepositories
public class DataJpaTestConfiguration {
}
