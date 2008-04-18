package net.paguo.domain.requests;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Reyentenko
 */
@Entity
@DiscriminatorValue("email")
public class EmailNotice extends Notice {
    private String email;
    private static final long serialVersionUID = -8784687873021281690L;

    @Column(name = "mail_address")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
