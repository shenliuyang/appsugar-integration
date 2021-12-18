package org.appsugar.integration.data.jpa.test;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.test
 * @className TestEntity
 * @date 2021-12-18  16:32
 */
@Data
@Entity
@Table(name = "test")
public class TestEntity {
    /**
     * id主键,mysql,db2,h2下会使用identity,  oracle 等 会使用sequence
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Getter
    @Setter
    private Long id;
    private String name;

}
