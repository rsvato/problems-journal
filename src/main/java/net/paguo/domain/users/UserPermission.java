package net.paguo.domain.users;


import javax.persistence.*;
import java.io.Serializable;

/**
 * @version $Id $
 * @hibernate.class table="user_perm"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="findAll" query="from UserPermission order by userName"
 */
@Embeddable
public class UserPermission implements Serializable {
    private String userName;
    private String digest;
    private String plainPassword;

    /**
     * @hibernate.id generator-class="assigned"
     * @return
     */
    @Column(name="username",unique = true)
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
    @Column(name="password", nullable = false)
    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    @Transient
    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
}
