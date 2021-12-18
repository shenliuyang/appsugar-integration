package org.appsugar.integration.data.jpa.test;

import org.appsugar.integration.data.jpa.test.configuration.DataBaseSampleImportConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * dataJpa 测试基类
 *
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa
 * @className BaseDataJpaTestcase
 * @date 2021-12-18  15:14
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(DataBaseSampleImportConfiguration.class)
public abstract class BaseDataJpaTestcase {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
