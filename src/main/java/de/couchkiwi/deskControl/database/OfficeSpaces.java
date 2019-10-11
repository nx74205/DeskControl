package de.couchkiwi.deskControl.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;

@Table("office_spaces")
public class OfficeSpaces {
    @Id
    int officeSpaceId;

    String officeSpaceName;

    @Column(value = "OFFICE_SPACE_ID")
    private Set<Desks> desksSet = new HashSet<>();

    public int getOfficeSpaceId() {
        return officeSpaceId;
    }

    public void setOfficeSpaceId(int officeSpaceId) {
        this.officeSpaceId = officeSpaceId;
    }

    public String getOfficeSpaceName() {
        return officeSpaceName;
    }

    public void setOfficeSpaceName(String officeSpaceName) {
        this.officeSpaceName = officeSpaceName;
    }

    public Set<Desks> getDesksSet() {
        return desksSet;
    }

    public void setDesksSet(Set<Desks> desksSet) {
        this.desksSet = desksSet;
    }
}
