package de.couchkiwi.deskControl.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Desks {
    @Id
    int deskId;

    String deskNumber;

    @Column(value = "OFFICE_SPACE_ID")
    int officeSpaceId;

    Long assignedUserId;

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public String getDeskNumber() {
        return deskNumber;
    }

    public void setDeskNumber(String deskNumber) {
        this.deskNumber = deskNumber;
    }

    public int getOfficeSpaceId() {
        return officeSpaceId;
    }

    public void setOfficeSpaceId(int officeSpaceId) {
        this.officeSpaceId = officeSpaceId;
    }

    public Long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(Long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}
