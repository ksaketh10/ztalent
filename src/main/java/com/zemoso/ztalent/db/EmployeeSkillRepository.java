package com.zemoso.ztalent.db;

import com.zemoso.ztalent.db.models.EmployeeSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillRepository extends CrudRepository<EmployeeSkill, Long> {

    @Query("select skillId from skillLookup where empId=?")
    List<Long> findSkillsByEmpId(Long id);
}
