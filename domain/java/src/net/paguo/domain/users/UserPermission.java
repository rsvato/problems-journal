package net.paguo.domain.users;

import java.io.Serializable;

/**
 * @version $Id $
 * @hibernate.class table="user_perm"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="findAll" query="from UserPermission order by userName"
 */
public class UserPermission implements Serializable {
    private String userName;
    private String digest;
    private String role;
    private Integer group;

    /**
     * @hibernate.id generator-class="assigned"
     * @return
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @hibernate.property column="password" not-null="true"
     * @return
     */
    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * @hibernate.property column="role" not-null="true"
     * @return
     */
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @hibernate.property column="grp"
     * @return
     */
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
