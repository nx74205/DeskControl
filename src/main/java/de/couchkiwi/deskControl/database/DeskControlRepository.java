package de.couchkiwi.deskControl.database;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DeskControlRepository extends CrudRepository<OfficeSpaces, Long> {

    @Query("select count(*) from desks " +
            "where desk_number = :deskNumber " +
            "and office_space_id = :officeSpaceId ")
    int countDesks(@Param("deskNumber") String deskNumber, @Param("officeSpaceId") int officeSpaceId);

    @Query("select office_space_id " +
            "from office_spaces " +
            "where upper(office_space_name) = :name")
    Optional<Long> findByOfficeSpaceName (@Param("name") String name);

}
