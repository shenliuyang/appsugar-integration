package org.appsugar.integration.data.jpa.repository;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;
import org.appsugar.integration.data.jpa.entity.AbstractIdEntity;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 所有repository的基类
 *
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa
 * @className BaseJpaRepository
 * @date 2021-04-08  18:57
 */
@NoRepositoryBean
public interface BaseJpaRepository<T extends AbstractIdEntity> extends EntityGraphJpaRepository<T, Long>, EntityGraphQuerydslPredicateExecutor {

    /**
     * 根据对象路径创建EntityGraph 仅供querydsl查询
     *
     * @param paths
     * @return
     */
    default EntityGraph graphFromPath(String... paths) {
        return EntityGraphUtils.fromAttributePaths(paths);
    }
}
