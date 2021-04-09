package org.appsugar.integration.data.jpa.repository;

import com.querydsl.core.BooleanBuilder;
import lombok.val;
import org.appsugar.integration.data.jpa.entity.Person;
import org.appsugar.integration.data.jpa.entity.PersonEntityGraph;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.repository
 * @className PersonRepositoryTest
 * @date 2021-04-09  10:31
 */
public class PersonRepositoryTest extends BaseJpaRepositoryTest {
    @Autowired
    PersonRepository repository;

    @Test
    public void testFindAndFetch() {
        val person = new Person();
        person.setName("test");
        person.setAddress("test");
        person.setAge(10);
        repository.save(person);
        val persons = repository.findAll(new BooleanBuilder(), PersonEntityGraph.____().pets().____.____());
        logger.debug("testFindAndFetch persons is {}", persons);
    }
}
