package com.zemoso.ztalent.db;

import com.zemoso.ztalent.db.models.SkillLookup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillLookupRepository extends CrudRepository<SkillLookup, Long> {

    @Query("select tag from skillLookup where id=?")
    String findTagById(Long id);

    @Query("select id from skillLookup where tag=?")
    Long findIdByTag(String tag);
}
