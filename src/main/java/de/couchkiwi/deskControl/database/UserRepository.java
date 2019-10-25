package de.couchkiwi.deskControl.database;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<Users, Long> {

    @Query("select * " +
            "from users " +
            "where upper(user_name) = :name")
    Optional<Users> findByUserName (@Param("name") String name);

}
