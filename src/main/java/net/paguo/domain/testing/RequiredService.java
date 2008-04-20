package net.paguo.domain.testing;

import org.hibernate.validator.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * User: sreentenko
 * Date: 20.04.2008
 * Time: 1:24:50
 */
@Embeddable
public class RequiredService implements Serializable {
    private Integer addressCount;
    private String trafficTypes; //to be clarified

    @Column(name = "ad_count")
    @NotNull
    public Integer getAddressCount() {
        return addressCount;
    }

    public void setAddressCount(Integer addressCount) {
        this.addressCount = addressCount;
    }

    @Column(name = "traffic_types")
    @NotNull
    public String getTrafficTypes() {
        return trafficTypes;
    }

    public void setTrafficTypes(String trafficTypes) {
        this.trafficTypes = trafficTypes;
    }
}
