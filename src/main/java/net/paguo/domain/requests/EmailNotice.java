package net.paguo.domain.requests;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

/**
 * @author Reyentenko
 */
@DiscriminatorValue("email")
public class EmailNotice extends Notice {
    private String email;
    private static final long serialVersionUID = -8784687873021281690L;

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
