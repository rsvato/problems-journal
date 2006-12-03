package net.paguo.domain.events;

import java.io.Serializable;
import java.util.Date;

/**
 * @version $Id $
 * @hibernate.class table="schedule_events"
 * @hibernate.cache usage="read-write"
 */
public class ScheduleEvent implements Serializable {
    private Integer id;
    private Date eventDate;
    private boolean completed;

    /**
     * @hibernate.id  generator-class="increment"
     * @return
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @hibernate.property not-null="true"
     * @return
     */
    public Date getEventDate() {
        return eventDate;
    }


    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * @hibernate.property not-null="true"
     * @return
     */
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
