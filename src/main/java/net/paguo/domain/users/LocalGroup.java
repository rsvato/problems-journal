package net.paguo.domain.users;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

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
    private Collection<LocalUser> users;
    private Collection<ApplicationRole> applicationRoles;

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
        targetEntity = ApplicationRole.class)
    @JoinTable(name="groups_aproles",
        joinColumns = @JoinColumn(name = "local_group_id"),
        inverseJoinColumns = @JoinColumn(name = "local_role_id"))
    public Collection<ApplicationRole> getApplicationRoles() {
        return applicationRoles;
    }

    public void setApplicationRoles(Collection<ApplicationRole> applicationRoles) {
        this.applicationRoles = applicationRoles;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = LocalUser.class, mappedBy = "groups")
    public Collection<LocalUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<LocalUser> users) {
        this.users = users;
    }
}
