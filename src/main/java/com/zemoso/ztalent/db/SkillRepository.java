package com.zemoso.ztalent.db;

import com.zemoso.ztalent.models.Skill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends CrudRepository<Skill, Long> {

    @Query("SELECT id FROM Skill WHERE tag=:tag")
    Long findIdByTag(String tag);
}
