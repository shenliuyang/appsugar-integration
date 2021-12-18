package org.appsugar.integration.data.jpa.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.test
 * @className ImportTest
 * @date 2021-12-18  16:31
 */

public class ImportTest extends BaseDataJpaTestcase {
    @Autowired
    private TestEntityRepository repository;

    @Test
    public void testImport() {
        logger.info("testImport all result is {}", repository.findAll());
    }
}
