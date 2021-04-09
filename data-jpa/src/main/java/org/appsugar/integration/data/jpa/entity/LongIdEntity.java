package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className LongIdEntity
 * @date 2021-04-08  19:21
 */
@MappedSuperclass
public class LongIdEntity extends IdEntity<Long> {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Getter
    @Setter
    protected Long id;

    @Override
    public String toString() {
        return "LongIdEntity{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}
