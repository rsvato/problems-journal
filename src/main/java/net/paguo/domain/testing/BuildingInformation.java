package net.paguo.domain.testing;

import net.paguo.visual.EditorEnum;
import net.paguo.visual.InterfaceField;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Reyentenko
 */
@Embeddable
public class BuildingInformation implements Serializable {
    @InterfaceField(editor = EditorEnum.LONGTEXT)
    private String buildingDescription;
    private static final long serialVersionUID = -7984083563134612178L;

    @Column(name = "building_info", columnDefinition = "TEXT")
    public String getBuildingDescription() {
        return buildingDescription;
    }

    public void setBuildingDescription(String buildingDescription) {
        this.buildingDescription = buildingDescription;
    }
}
