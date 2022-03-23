package org.appsugar.integration.data.jpa.entity;

import org.jetbrains.annotations.Nullable;

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
public abstract class AbstractIdEntity extends IdEntity {
    private long id;
    private Date createdAt;
    private Date updatedAt;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Nullable
    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
