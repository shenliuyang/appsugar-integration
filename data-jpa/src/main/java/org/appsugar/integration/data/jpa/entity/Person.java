package org.appsugar.integration.data.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.entity
 * @className Person
 * @date 2021-04-08  19:40
 */
@Entity
@Table(name = Person.TABLE_NAME)
@DynamicUpdate
@Getter
@Setter
public class Person extends AutoLongIdEntity {
    public static final String TABLE_NAME = "person";
    private Integer age;
    private String name;
    private String address;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = Pet_.PERSON)
    private Set<Pet> pets;

    @Override
    public String toString() {
        return "Person{" +
                "createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
