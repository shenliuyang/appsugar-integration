package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.appsugar.integration.data.jpa.listener.SaveEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa
 * @className IdEntity
 * @date 2021-04-08  19:13
 */
@EntityListeners(SaveEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class IdEntity<ID extends Serializable> {
    @Column(name = "created_at")
    protected Date createdAt;
    @Column(name = "updated_at")
    protected Date updatedAt;

    public abstract ID getId();

    public abstract void setId(ID id);
}
