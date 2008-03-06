package net.paguo.domain.clients;

import javax.persistence.Entity;
import javax.persistence.DiscriminatorValue;

/**
 * @author Reyentenko
 */
@Entity
@DiscriminatorValue(value = "equipment")
public class EquipmentItem extends AbstractItem {
}
