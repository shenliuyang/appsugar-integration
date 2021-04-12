package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className AutoLongIdEntity
 * @date 2021-04-08  19:21
 */
@MappedSuperclass
public class AutoLongIdEntity extends IdEntity<Long> {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @SequenceGenerator(name = "native", initialValue = 1, allocationSize = 1000)
    @Getter
    @Setter
    protected Long id;

    @Override
    public String toString() {
        return "AutoLongIdEntity{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                '}';
    }
}
