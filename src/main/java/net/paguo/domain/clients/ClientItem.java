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
 * @author Svyatoslav Reyentenko mailto:rsvato@gmail.com
 * @version $Id$
 *          created 22.08.2006 23:20:25
 * @hibernate.class table="cl"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="ClientItem.findByName" query="from ClientItem where clientName = ?"
 * @hibernate.query name="ClientItem.findAll" query="from ClientItem where deleted = false"
 */
@Entity
@Table(name = "cl")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NamedQueries(
        {@NamedQuery(name = "ClientItem.findActive",
                query = "select cl from ClientItem cl where cl.deleted = false order by cl.clientName"),
        @NamedQuery(name = "ClientItem.findByName",
                query = "select cl from ClientItem  cl where lower(cl.clientName) = lower(:name)"),
        @NamedQuery(name = "ClientItem.findForRequest",
                query = "select distinct client from ClientItem client where client.deleted = false and client.addresses.size > 0 order by client.clientName")
                }
)
public class ClientItem implements Serializable, Comparable<ClientItem> {
    private Integer id;
    private String clientName;
    private Boolean deleted;
    private Set<PostalAddress> addresses;

    /**
     * @return
     * @hibernate.id generator-class="increment"
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

        ClientItem item = (ClientItem) o;

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

    public int compareTo(ClientItem clientItem) {
        return clientName.compareTo(clientItem.getClientName());
    }
}
