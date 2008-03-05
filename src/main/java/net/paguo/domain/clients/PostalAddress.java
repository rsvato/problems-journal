package net.paguo.domain.clients;

import javax.persistence.*;
import java.io.Serializable;
import java.text.MessageFormat;

/**
 * $Author slava
 *
 * @version $Id $
 * @hibernate.class table="addresses"
 * dynamic-insert = "true"
 * dynamic-update = "true"
 */
@Entity
@Table(name = "addresses")
public class PostalAddress implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3546638811116156728L;
    private Integer id;
    private ClientItem client;
    private String country;
    private String zip;
    private String street;
    private String house;
    private String apartment;
    private String poBox;
    private String corrName;
    private String city;

    /**
     * @return
     * @hibernate.id generator-class="increment" column="id"
     */
    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return
     * @hibernate.many-to-one column="client_id" cascade="none" not-null="true"
     */
    @ManyToOne
    @JoinColumn(name = "client_id",
            updatable = false, nullable = false)
    public ClientItem getClient() {
        return client;
    }

    public void setClient(ClientItem client) {
        this.client = client;
    }

    /**
     * @return
     * @hibernate.property column="country"
     */
    @Column
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return
     * @hibernate.property column="zip"
     */
    @Column
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return
     * @hibernate.property column="street"
     */
    @Column(nullable = false)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * @return
     * @hibernate.property column="house"
     * not-null="false"
     * unique="false"
     */
    @Column(nullable = false)
    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * @return
     * @hibernate.property column="aprtmt"
     * not-null="false"
     * unique="false"
     */
    @Column(name = "aprtmt")
    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    /**
     * @return
     * @hibernate.property column="post_box"
     * not-null="false"
     * unique="false"
     */
    @Column(name = "post_box")
    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    /**
     * @return
     * @hibernate.property column="corr_name"
     * not-null="false"
     * unique="false"
     */
    @Column(name = "corr_name")
    public String getCorrName() {
        return corrName;
    }

    public void setCorrName(String corrName) {
        this.corrName = corrName;
    }


    /**
     * @return Returns the city.
     * @hibernate.property column = "city" not-null = "true"
     */

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    /**
     * @param city The city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostalAddress)) return false;

        final PostalAddress postalAddress = (PostalAddress) o;
        if (id != null && !id.equals(postalAddress.id)) return false;
        return id != null;
    }

    public int hashCode() {
        int result;
        result = id == null ? 29 : id.hashCode();
        return result;
    }

    public String toString() {
        return MessageFormat.format("{0}, {1}, {2}, {3}",
                String.valueOf(city), String.valueOf(street),
                String.valueOf(house), String.valueOf(apartment));
    }
}
