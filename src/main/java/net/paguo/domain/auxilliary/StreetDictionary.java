package net.paguo.domain.auxilliary;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Reyentenko
 */
@Entity
@Table(name = "streets")
@NamedQueries({@NamedQuery(name = "StreetDictionary.findOrdered",
        query = "select st from StreetDictionary st order by name"),
@NamedQuery(name = "StreetDictionary.findByName",
        query = "select st from StreetDictionary st where upper(name) like upper(:name) order by name")})
public class StreetDictionary implements Serializable {
    private Integer id;
    private String name;
    private static final long serialVersionUID = 1269680974958669046L;

    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer v) {
        this.id = v;
    }

    @Column(name = "street_name", unique = true)
    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }
}
