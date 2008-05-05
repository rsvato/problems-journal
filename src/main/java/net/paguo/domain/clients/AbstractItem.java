package net.paguo.domain.clients;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Reyentenko
 */
@Entity
@Table(name = "cl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "record_type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractItem implements Serializable {
    private Integer id;
    private String clientName;
    private Boolean deleted;
    private Set<PostalAddress> addresses;
    private static final long serialVersionUID = 521272128938766437L;

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
     * @hibernate.property column="client" not-null="true"
     */
    @NotNull
    @Column(name = "client")
    @Field(index = Index.TOKENIZED, name = "client")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @return
     * @hibernate.property column="deleted"
     */
    @Column
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @OneToMany(targetEntity = PostalAddress.class, mappedBy = "client")
    public Set<PostalAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<PostalAddress> addresses) {
        this.addresses = addresses;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractItem item = (AbstractItem) o;

        if (!clientName.equals(item.clientName)) return false;
        if (deleted != null ? !deleted.equals(item.deleted) : item.deleted != null) return false;
        return id.equals(item.id);

    }

    public int hashCode() {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (clientName != null ? clientName.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }

    public int compareTo(AbstractItem clientItem) {
        return clientName.compareTo(clientItem.getClientName());
    }
}
