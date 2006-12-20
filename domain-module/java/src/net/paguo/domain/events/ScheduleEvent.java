package net.paguo.domain.events;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.NotNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.Date;

/**
 * @version $Id $
 * @hibernate.class table="schedule_events"
 * @hibernate.cache usage="read-write"
 */
@Entity
@Table(name="schedule_events")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ScheduleEvent implements Serializable {
    private Integer id;
    private Date eventDate;
    private boolean completed;

    /**
     * @hibernate.id  generator-class="increment"
     * @return
     */
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment",strategy = "increment")
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
    @NotNull
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
    @NotNull
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
