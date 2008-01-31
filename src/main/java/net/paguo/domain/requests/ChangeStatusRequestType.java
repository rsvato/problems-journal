package net.paguo.domain.requests;

import org.hibernate.validator.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

/**
 * User: slava
 * Date: 24.12.2006
 * Time: 23:11:29
 * Version: $Id$
 */
@Entity
@Table(name="request_types")
public class ChangeStatusRequestType {
    private Integer id;
    private String description;

    @Id @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
