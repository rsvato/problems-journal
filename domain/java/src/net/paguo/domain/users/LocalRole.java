package net.paguo.domain.users;

import java.io.Serializable;

/**
 * @version $Id $
 * @hibernate.class table="local_role"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="LocalRole.findAll" query="from LocalRole"
 * @hibernate.query name="LocalRole.findByName" query="from LocalRole where name= ?"
 */
public class LocalRole implements Serializable {
    private Integer id;
    private String role;
    private String roleDescription;

    /**
     * @hibernate.id generator-class ="increment"
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.property
     * @return
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @hibernate.property 
     * @return
     */
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
