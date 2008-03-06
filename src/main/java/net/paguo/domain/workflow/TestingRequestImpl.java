package net.paguo.domain.workflow;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author Reyentenko
 */
@Entity
@Table("testing_requests")
@PrimaryKeyJoinColumn(name = "req_id")
public class TestingRequestImpl extends GenericRequest {
    private static final long serialVersionUID = -1606083152345982965L;
}
