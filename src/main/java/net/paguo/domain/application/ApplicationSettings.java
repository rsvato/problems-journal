package net.paguo.domain.application;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 19.11.2007
 * Time: 23:54:50
 */
@Entity
@Table(name = "application_settings", uniqueConstraints = @UniqueConstraint(columnNames = {"key"}))
@NamedQueries(
        {
        @NamedQuery(name = "ApplicationSettings.findByKey", query = "from ApplicationSettings ast where ast.key = :key")
                }
)
public class ApplicationSettings implements Serializable {
    private Integer id;
    private String key;
    private String value;

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApplicationSettings)) return false;

        ApplicationSettings that = (ApplicationSettings) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (id != null ? id.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
