package com.zemoso.ztalent.db;

import com.zemoso.ztalent.db.models.EmployeeRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRecordRepository extends CrudRepository<EmployeeRecord, Long> {

    @Query("SELECT id FROM EmployeeRecord e WHERE e.firstName=:firstName AND e.lastName=:lastName")
    Long getIdByFirstNameAndLastName(String firstName, String lastName);
}
