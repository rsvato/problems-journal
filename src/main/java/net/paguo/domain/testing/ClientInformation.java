package net.paguo.domain.testing;

import net.paguo.visual.EditorEnum;
import net.paguo.visual.InterfaceField;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

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

    @InterfaceField(order = 1, editor = EditorEnum.ENUM) @NotNull
    private ClientTypeEnum clientType;

    @InterfaceField(order = 0, editor = EditorEnum.STRING) @NotNull @Length(min=6, max=30)
    private String clientDesignation;
    @Valid
    private AddressInformation address;
    @Valid
    private ClientContactInformation contact;
    private static final long serialVersionUID = -3901014933364138921L;

    @Id @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer vinteger) {
        this.id = vinteger;
    }

    @Column(name="client_type")
    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum type) {
        this.clientType = type;
    }

    @Embedded
    public AddressInformation getAddress() {
        return address;
    }

    public void setAddress(AddressInformation information) {
        this.address = information;
    }

    @Embedded
    public ClientContactInformation getContact() {
        return contact;
    }

    public void setContact(ClientContactInformation contactInformation) {
        this.contact = contactInformation;
    }

    @Column(name="designation")
    public String getClientDesignation() {
        return clientDesignation;
    }

    public void setClientDesignation(String s) {
        this.clientDesignation = s;
    }
}
