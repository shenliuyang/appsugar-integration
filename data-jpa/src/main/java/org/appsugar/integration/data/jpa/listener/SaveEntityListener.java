package org.appsugar.integration.data.jpa.listener;

import lombok.val;
import org.appsugar.integration.data.jpa.entity.IdEntity;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.listener
 * @className SaveEntityListener
 * @date 2021-04-09  11:31
 */
@Configurable
public class SaveEntityListener {
    public SaveEntityListener() {
       
    }

    @PrePersist
    public void prePersist(IdEntity<Serializable> entity) {
        val current = new Date();
        entity.setCreatedAt(current);
        entity.setUpdatedAt(current);
    }

    @PreUpdate
    public void preUpdate(IdEntity<Serializable> entity) {
        entity.setUpdatedAt(new Date());
    }

}
