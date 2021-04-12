package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 在mysql提供高性能id生成,支持批量导入
 * 建议使用本地ID生成,在集群环境下,注意配置workId与dataCenterId
 *
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className LongIdEntity
 * @date 2021-04-12  21:20
 */
@MappedSuperclass
public class LongIdEntity extends IdEntity<Long> {
    @Id
    @org.springframework.data.annotation.Id
    @GenericGenerator(name = "snowflake", strategy = "org.appsugar.integration.data.jpa.generator.SnowflakeIdGenerator")
    @GeneratedValue(generator = "snowflake")
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
