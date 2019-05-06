package com.zemoso.ztalent.db;

import com.zemoso.ztalent.db.models.EmployeeSkill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSkillRepository extends CrudRepository<EmployeeSkill, Long> {

    @Query(value = "SELECT * FROM EmployeeSkill WHERE emp_id=:id", nativeQuery = true)
    List<EmployeeSkill> findEmployeeSkills(Long id);
}
