package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className Pet
 * @date 2021-04-09  10:17
 */
@Entity
@Table(name = Pet.TABLE_NAME)
@DynamicUpdate
@Getter
@Setter
public class Pet extends LongIdEntity {
    public static final String TABLE_NAME = "pet";
    private String name;
    private Integer age;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public String toString() {
        return "Pet{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", person=" + person.getId() +
                '}';
    }
}
