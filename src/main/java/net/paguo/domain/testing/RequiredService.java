package net.paguo.domain.testing;

import net.paguo.visual.EditorEnum;
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
    @InterfaceField(order = 0) @NotNull
    private Integer addressCount;

    @InterfaceField(order = 1, editor = EditorEnum.LONGTEXT) @NotNull
    private String trafficTypes; //to be clarified
    private static final long serialVersionUID = -3256240782732181192L;

    @Column(name = "ad_count")
    public Integer getAddressCount() {
        return addressCount;
    }

    public void setAddressCount(Integer count) {
        this.addressCount = count;
    }

    @Column(name = "traffic_types")
    public String getTrafficTypes() {
        return trafficTypes;
    }

    public void setTrafficTypes(String types) {
        this.trafficTypes = types;
    }
}
