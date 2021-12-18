package org.appsugar.integration.data.jpa.test;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.test
 * @className TestEntityRepository
 * @date 2021-12-18  16:40
 */
public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}
