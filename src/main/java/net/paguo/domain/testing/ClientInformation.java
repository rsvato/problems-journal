package net.paguo.domain.testing;

import org.hibernate.validator.NotNull;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 30.03.2008
 * Time: 0:47:53
 */
@Entity
@Table(name = "wf_ccinfo")
public class ClientInformation implements Serializable {
    private Integer id;
    private ClientTypeEnum clientType;
    private AddressInformation address;
    private ClientContactInformation contact;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum clientType) {
        this.clientType = clientType;
    }

    @Embedded
    @NotNull
    public AddressInformation getAddress() {
        return address;
    }

    public void setAddress(AddressInformation address) {
        this.address = address;
    }

    @Embedded
    @NotNull
    public ClientContactInformation getContact() {
        return contact;
    }

    public void setContact(ClientContactInformation contact) {
        this.contact = contact;
    }
}
