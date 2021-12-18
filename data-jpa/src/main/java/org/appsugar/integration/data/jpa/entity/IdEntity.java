package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 基础对象类
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa
 * @className IdEntity
 * @date 2021-04-08  19:13
 */
@MappedSuperclass
@Getter
@Setter
public abstract class IdEntity<ID extends Serializable> {
    /**
     *  id主键,mysql,db2,h2下会使用identity,  oracle 等 会使用sequence
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Getter
    @Setter
    protected Long id;

    @CreationTimestamp
    protected Date createdAt;
    @UpdateTimestamp
    protected Date updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdEntity that = (IdEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }


}
