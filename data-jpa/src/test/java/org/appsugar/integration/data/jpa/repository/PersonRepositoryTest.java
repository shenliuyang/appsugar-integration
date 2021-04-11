package org.appsugar.integration.data.jpa.repository;

import com.querydsl.core.BooleanBuilder;
import lombok.val;
import org.appsugar.integration.data.jpa.entity.Person;
import org.appsugar.integration.data.jpa.entity.PersonEntityGraph;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
    @Autowired
    PersonStatRepository statRepository;

    @Test
    public void testFindByPredicate() {
        val predicate = statRepository.topredicate();
        List<Person> result = (List<Person>) repository.findAll(predicate, PersonEntityGraph.____().pets().____.____());
        logger.debug("testFindByPredicate result is {} pet size {}", result, result.get(0).getPets().size());
    }

    @Test
    public void testFindPersonPetSize() {
        val result = statRepository.findPersonPetSize();
        logger.debug("testFindPersonPetSize result is {}", result);
    }

    @Test
    public void testFindPetsGreaterThan() {
        val result = statRepository.findPetsGreaterThan(2);
        logger.debug("testFindPetsGreaterThan result is {}", result);
    }

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
