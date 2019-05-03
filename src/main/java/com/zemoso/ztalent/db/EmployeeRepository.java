package com.zemoso.ztalent.db;

import com.zemoso.ztalent.db.models.EmployeeRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeRecord, Long> {
}
