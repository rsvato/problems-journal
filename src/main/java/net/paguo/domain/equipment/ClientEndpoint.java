package net.paguo.domain.equipment;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Client vlan. Used as disable point in ChangeStatusRequest objects....
 */
@Entity
@Table(name = "endpoints",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"designation", "vlan_num"})})
@NamedQueries({@NamedQuery(name = "ClientEndpoint.findByDesignationAndNumber",
        query = "select ce from ClientEndpoint ce where ce.designation = :des and ce.vlanNumber = :vlan")})
public class ClientEndpoint implements Serializable {
    private Integer id;
    private String designation;
    private Integer vlanNumber;
    private static final long serialVersionUID = -4634311432420708402L;


    @Id
    @GeneratedValue(generator = "seq")
    @GenericGenerator(name = "seq", strategy = "sequence",
            parameters = {@Parameter(name = "sequence",
                    value = "endpoints_seq")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "designation", nullable = false)
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Column(name = "vlan_num", nullable = false)
    public Integer getVlanNumber() {
        return vlanNumber;
    }

    public void setVlanNumber(Integer vlanNumber) {
        this.vlanNumber = vlanNumber;
    }

    public String toString() {
        return MessageFormat.format("{0} {1}", designation, vlanNumber);
    }
}
