package net.paguo.domain.users;



import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @version $Id $
 * @hibernate.class table="local_role"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="LocalRole.findAll" query="from LocalRole"
 * @hibernate.query name="LocalRole.findByName" query="from LocalRole where role = ?"
 */
@Entity
@Table(name = "local_role", uniqueConstraints= {@UniqueConstraint(columnNames = {"role"}),
        @UniqueConstraint(columnNames = {"roleDescription"})} )
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.NamedQuery(name="LocalRole.findByName",query="from LocalRole where name = ?")
public class LocalRole implements Serializable {
    protected Integer id;
    protected String role;
    protected String roleDescription;

    /**
     * @hibernate.id generator-class ="increment"
@     */
    @Id @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
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
    @Column(nullable = false, unique = true)
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
    @Column (nullable = false, unique = true)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
