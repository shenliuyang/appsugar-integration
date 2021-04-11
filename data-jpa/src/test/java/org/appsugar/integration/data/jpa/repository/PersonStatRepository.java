package org.appsugar.integration.data.jpa.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.val;
import org.appsugar.integration.data.jpa.entity.Person;
import org.appsugar.integration.data.jpa.entity.PersonPetStatDTO;
import org.appsugar.integration.data.jpa.entity.QPerson;
import org.appsugar.integration.data.jpa.entity.QPet;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author shenliuyang
 * @version 1.0.0
 * @package org.appsugar.integration.data.jpa.repository
 * @className PersonStatRepository
 * @date 2021-04-09  16:14
 */
@Component
public class PersonStatRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 查找宠物数大于等于size的人
     *
     * @param size
     * @return java.util.List<org.appsugar.integration.data.jpa.entity.Person>
     * @throws
     * @method
     * @author shenliuyang
     * @date 2021/4/9 16:22
     */
    public List<Person> findPetsGreaterThan(Integer size) {
        val p = QPerson.person;
        return new JPAQuery<Person>(em).from(p).leftJoin(p.pets).groupBy(p.id).having(p.id.count().goe(size)).fetch();
    }

    public List<PersonPetStatDTO> findPersonPetSize() {
        val p = QPerson.person;
        val pet = QPet.pet;
        return new JPAQuery<Person>(em).from(p).leftJoin(p.pets, pet).groupBy(p.id)
                .select(Projections.bean(PersonPetStatDTO.class, p.id, p.name, pet.id.count().as("petSize"))).fetch();
    }


    public Predicate topredicate() {
        val p = new BooleanBuilder();
        p.and(QPerson.person.pets.any().name.eq("dog"));
        return p;
    }

}
