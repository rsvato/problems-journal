package net.paguo.domain.common;

import org.hibernate.validator.NotNull;

import javax.persistence.Embeddable;
import javax.persistence.Column;
import java.io.Serializable;

/**
 * @version $Id $
 */
@Embeddable
public class PersonalData implements Serializable {
    private String name;
    private String familyName;
    private String parentName;

    
    /**
     * @hibernate.property not-null="true"
     * @return
     */
    @NotNull
    @Column
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
    @NotNull
    @Column
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
    @Column
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String toString(){
        StringBuilder result = new StringBuilder();
        if (familyName != null){
            result.append(familyName);
        }
        return result.toString();
    }
}
