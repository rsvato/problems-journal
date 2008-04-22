package net.paguo.domain.testing;

import net.paguo.visual.EditorEnum;
import net.paguo.visual.InterfaceField;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
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

    @InterfaceField(order = 1, editor = EditorEnum.ENUM)
    private ClientTypeEnum clientType;

    @InterfaceField(order = 0, editor = EditorEnum.STRING)
    private String clientDesignation;
    private AddressInformation address;
    private ClientContactInformation contact;
    private static final long serialVersionUID = -3901014933364138921L;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    @Column(name="client_type")
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

    @Column(name="designation")
    @NotNull
    public String getClientDesignation() {
        return clientDesignation;
    }

    public void setClientDesignation(String clientDesignation) {
        this.clientDesignation = clientDesignation;
    }
}
