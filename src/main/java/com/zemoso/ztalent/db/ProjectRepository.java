package com.zemoso.ztalent.db;

import com.zemoso.ztalent.models.Project;
import com.zemoso.ztalent.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

    @Query("SELECT id FROM Project WHERE title=:title")
    Long findIdByProject(String title);
}
