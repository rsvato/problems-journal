package net.paguo.domain.users;

import net.paguo.domain.common.ContactData;
import net.paguo.domain.common.PersonalData;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @version $Id $
 * @hibernate.class table="local_users"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="LocalUser.findAll" query="from LocalUser order by familyName"
 * @hibernate.query name="LocalUser.findByPermission" query="from LocalUser where permissionEntry = ?"
 */
@Entity
@Table(name="local_users")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQuery(name = "LocalUser.findByPermission", query = "select lu from LocalUser lu where lu.permissionEntry.userName = :uname")
public class LocalUser implements Serializable {
    private Integer id;
    private PersonalData personalData;
    private ContactData contactData;
    private String description;
    private Collection<LocalGroup> groups;
    private UserPermission permissionEntry;
    private static final long serialVersionUID = 6815284993616376539L;


    public LocalUser(){
        this.personalData = new PersonalData();
        this.contactData = new ContactData();
        this.permissionEntry = new UserPermission();
    }
    /**
     * @hibernate.id generator-class="increment"
     * @return id 
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.component class="net.paguo.domain.common.PersonalData"
     * @return  personal data
     */
    @Embedded
    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    /**
     * @hibernate.property
     * @return  description
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
     * @return groups set
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        targetEntity = LocalGroup.class)
    @JoinTable(name="groups_users",
        joinColumns = @JoinColumn(name = "local_user_id"),
        inverseJoinColumns = @JoinColumn(name = "local_group_id"))
    public Collection<LocalGroup> getGroups() {
        return groups;
    }

    public void setGroups(Collection<LocalGroup> groups) {
        this.groups = groups;
    }

    
    @Embedded
    public UserPermission getPermissionEntry() {
        return permissionEntry;
    }

    public void setPermissionEntry(UserPermission permissionEntry) {
        this.permissionEntry = permissionEntry;
    }

    /**
     * @hibernate.component class="net.paguo.domain.common.ContactData"
     * @return contact data
     */
    @Embedded
    public ContactData getContactData() {
        return contactData;
    }

    public void setContactData(ContactData contactData) {
        this.contactData = contactData;
    }

    public String toString(){
        if (personalData != null && StringUtils.isNotEmpty(personalData.toString())){
           return personalData.toString();    
        }else{
            return getPermissionEntry().getUserName();
        }
    }

    @Transient
    public Set<String> getAuthorities(){
        Set<String> roles = new TreeSet<String>();
        for (LocalGroup group : groups) {
            for (AbstractRole role : group.getRoles()) {
               roles.add(role.getRole());
            }

            for (AbstractRole role : group.getApplicationRoles()){
                roles.add(role.getRole());
            }
        }

        return roles;
    }
}
