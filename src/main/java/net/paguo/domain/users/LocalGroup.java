package net.paguo.domain.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * User: sreentenko
 * Date: 04.12.2007
 * Time: 2:00:24
 */
@Entity
@Table(name = "local_groups")
public class LocalGroup implements Serializable {
    private Long id;
    private String groupName;
    private Collection<LocalRole> roles;
    private Set<LocalUser> users;

    @Id @GeneratedValue
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "group_name", nullable = false)
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = LocalRole.class)
    @JoinTable(name="groups_roles",
        joinColumns = @JoinColumn(name = "local_group_id"),
        inverseJoinColumns = @JoinColumn(name = "local_role_id"))
    public Collection<LocalRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<LocalRole> roles) {
        this.roles = roles;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = LocalUser.class)
    @JoinTable(name="groups_users",
        joinColumns = @JoinColumn(name = "local_group_id"),
        inverseJoinColumns = @JoinColumn(name = "local_user_id"))
    public Set<LocalUser> getUsers() {
        return users;
    }

    public void setUsers(Set<LocalUser> users) {
        this.users = users;
    }
}
