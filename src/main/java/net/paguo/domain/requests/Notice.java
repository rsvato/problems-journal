package net.paguo.domain.requests;

import java.util.Date;

/**
 * @author Reyentenko
 */

public abstract class Notice {
    private Integer id;
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
