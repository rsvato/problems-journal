package net.paguo.domain.common;

import java.io.Serializable;

/**
 * @version $Id $
 */
public class PersonalData implements Serializable {
    private String name;
    private String familyName;
    private String parentName;
    
    /**
     * @hibernate.property not-null="true"
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @hibernate.property not-null="true"
     * @return
     */
    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * @hibernate.property
     * @return
     */
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
