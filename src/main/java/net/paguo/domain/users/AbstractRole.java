package net.paguo.domain.users;

import org.hibernate.annotations.GenericGenerator;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

/**
 * User: sreentenko Date: 13.01.2008 Time: 2:20:10
 */
@Entity
@Table(name = "local_role", uniqueConstraints = {
@UniqueConstraint(columnNames = {"role"}),
@UniqueConstraint(columnNames = {"roleDescription"}
)})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractRole {
    protected Integer id;
    protected String role;
    protected String roleDescription;


    /**
     * @hibernate.id generator-class ="increment"
     * @
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     * @hibernate.property
     */
    @Column(nullable = false, unique = true)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return
     * @hibernate.property
     */
    @Column(nullable = false, unique = true)
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String toString() {
        return StringUtils.isEmpty(roleDescription) ? role : roleDescription;
    }


}
