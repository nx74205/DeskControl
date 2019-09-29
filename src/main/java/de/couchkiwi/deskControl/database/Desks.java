package de.couchkiwi.deskControl.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Desks {
    @Id
    @Column("DESK_ID")
    int deskid;

    @Column("DESK_NUMBER")
    String deskNumber;

    @Column("OFFICE_SPACE_ID")
    int officeSpaces;

    @Column("ASSIGNED_USER_ID")
    int assignedUserId;
}
