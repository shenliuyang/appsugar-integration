package org.appsugar.integration.data.jpa.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className AbstractIdEntity
 * @date 2022-01-12  15:51
 */
@MappedSuperclass
public abstract class AbstractIdEntity {
    protected Long id;
    @CreatedDate
    protected Date createdAt;
    @LastModifiedDate
    protected Date updatedAt;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Date getUpdatedAt() {
        return updatedAt;
    }


    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
