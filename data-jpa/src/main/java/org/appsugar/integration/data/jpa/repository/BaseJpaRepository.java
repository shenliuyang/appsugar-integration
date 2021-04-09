package org.appsugar.integration.data.jpa.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import org.appsugar.integration.data.jpa.entity.IdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa
 * @className BaseJpaRepository
 * @date 2021-04-08  18:57
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends IdEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID>, EntityGraphQuerydslPredicateExecutor {

}
