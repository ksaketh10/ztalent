package com.zemoso.ztalent.db;

import com.zemoso.ztalent.models.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Query("SELECT id FROM Employee e WHERE e.empId=:empId")
    Long getIdByEmpId(Long empId);
}
