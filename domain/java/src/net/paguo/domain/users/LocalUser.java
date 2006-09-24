package net.paguo.domain.users;

import net.paguo.domain.common.PersonalData;

import java.io.Serializable;
import java.util.Set;

/**
 * @version $Id $
 * @hibernate.class table="local_users"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="LocalUser.findAll" query="from LocalUser order by familyName"
 * @hibernate.query name="LocalUser.findByPermission" query="from LocalUser where permissionEntry = ?"
 * //TODO: name-fn-pn must be a component or hibernate type
 */
public class LocalUser implements Serializable {
    private Integer id;
    private PersonalData personalData;
    private String description;
    private Set<LocalRole> roles;
    private UserPermission permissionEntry;

    /**
     * @hibernate.id generator-class="increment"
     * @return
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.component class="net.paguo.domain.common.PersonalData"
     * @return
     */
    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    /**
     * @hibernate.property
     * @return
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @hibernate.set table="roles_users" cascade="save-update"
     * @hibernate.collection-key column="local_user_id"
     * @hibernate.collection-many-to-many class="net.paguo.domain.users.LocalRole" column="local_role_id"
     * @return
     */
    public Set<LocalRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<LocalRole> roles) {
        this.roles = roles;
    }

    /**
     * @hibernate.many-to-one class="net.paguo.domain.users.UserPermission" cascade="save-update"
     *  column="prm" not-null="true"
     * @return
     */
    public UserPermission getPermissionEntry() {
        return permissionEntry;
    }

    public void setPermissionEntry(UserPermission permissionEntry) {
        this.permissionEntry = permissionEntry;
    }
}
