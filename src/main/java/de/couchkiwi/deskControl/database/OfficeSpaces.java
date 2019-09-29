package de.couchkiwi.deskControl.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("office_spaces")
public class OfficeSpaces {
    @Id
    @Column("office_space_id")
    int officeSpaceId;

    @Column("office_space_name")
    String officeSpaceName;

}
