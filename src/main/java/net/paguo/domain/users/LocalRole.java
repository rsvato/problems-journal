package net.paguo.domain.users;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @version $Id $
 * @hibernate.class table="local_role"
 * @hibernate.cache usage="read-write"
 * @hibernate.query name="LocalRole.findAll" query="from LocalRole"
 * @hibernate.query name="LocalRole.findByName" query="from LocalRole where role = ?"
 */
@Entity
@DiscriminatorValue("service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.hibernate.annotations.NamedQuery(name="LocalRole.findByName",query="from LocalRole where name = ?")
public class LocalRole extends AbstractRole implements Serializable {
}
