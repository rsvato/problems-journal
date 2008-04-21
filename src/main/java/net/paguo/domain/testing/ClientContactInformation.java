package net.paguo.domain.testing;

import net.paguo.visual.InterfaceField;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:23:10
 */
@Embeddable
public class ClientContactInformation implements Serializable {
    @InterfaceField
    private String fullName;

    @InterfaceField
    private String phone;

    @InterfaceField
    private String email;
    private static final long serialVersionUID = 983061495991431305L;

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "phone", nullable = false)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
