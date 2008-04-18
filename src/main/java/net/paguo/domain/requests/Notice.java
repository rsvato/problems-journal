package net.paguo.domain.requests;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Reyentenko
 */
@Entity
@Table(name="disc_notices")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name="notice_type")
public abstract class Notice implements Serializable {
    private Integer id;
    private Date date;
    private static final long serialVersionUID = 314942170995320986L;

    @Id
    @GeneratedValue(generator = "seq")
    @GenericGenerator(name = "seq", strategy = "sequence",
            parameters = {@Parameter(name = "sequence",
                    value = "wrq_seq")})
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "dat")
    @Temporal(value = TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
