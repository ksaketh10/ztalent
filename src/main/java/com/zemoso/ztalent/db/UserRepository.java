package com.zemoso.ztalent.db;

import com.zemoso.ztalent.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT id FROM User WHERE email=:email")
    Long findIdByEmail(String email);
}
