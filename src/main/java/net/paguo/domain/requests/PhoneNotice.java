package net.paguo.domain.requests;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

/**
 * @author Reyentenko
 */
@DiscriminatorValue("phone")
public class PhoneNotice extends Notice {
    private String phone;
    private static final long serialVersionUID = -2133958707686719096L;

    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
