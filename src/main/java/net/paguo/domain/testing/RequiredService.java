package net.paguo.domain.testing;

import net.paguo.visual.InterfaceField;
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
    @InterfaceField(order = 0)
    private Integer addressCount;

    @InterfaceField(order = 1)
    private String trafficTypes; //to be clarified
    private static final long serialVersionUID = -3256240782732181192L;

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
