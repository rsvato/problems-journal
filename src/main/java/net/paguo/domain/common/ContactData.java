package net.paguo.domain.common;

import javax.persistence.Embeddable;
import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;

/**
 * User: slava
 * Date: 22.10.2006
 * Time: 0:42:53
 * Version: $Id$
 */
@Embeddable
public class ContactData implements Serializable {
    private String email;
    private String phone;
    private String mobilePhone;
    private String workPhone;
    private String contactComments;


    /**
     * @hibernate.property
     * @return
     */
    @Column
    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }


    /**
     * @hibernate.property type="text"
     * @return
     */
    @Column @Lob
    public String getContactComments() {
        return contactComments;
    }

    public void setContactComments(String contactComments) {
        this.contactComments = contactComments;
    }

    /**
     * @hibernate.property 
     * @return
     */
    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @hibernate.property
     * @return
     */
    @Column
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @hibernate.property 
     * @return
     */
    @Column
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
