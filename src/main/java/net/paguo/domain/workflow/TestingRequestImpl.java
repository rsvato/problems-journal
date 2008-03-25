package net.paguo.domain.workflow;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Reyentenko
 */
@Entity
@Table(name = "testing_requests")
@PrimaryKeyJoinColumn(name = "req_id")
public class TestingRequestImpl extends GenericRequest {
    private static final long serialVersionUID = -1606083152345982965L;
}
