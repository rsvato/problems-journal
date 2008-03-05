package net.paguo.dao.impl;

import net.paguo.domain.requests.RequestInformationType;
import net.paguo.domain.requests.ChangeStatusRequest;
import net.paguo.domain.requests.ChangeRequestType;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * User: sreentenko
 * Date: 21.02.2008
 * Time: 1:27:14
 */
public class TestFieldFinder {

    @Test
    public void testFindField() throws NoSuchFieldException {
        for (RequestInformationType type : RequestInformationType.values()){
            for (ChangeRequestType requestType : ChangeRequestType.values()){
                String propertyName = ChangeStatusRequestDaoImpl.findProperty(type, requestType);
                assertNotNull("Property for " + type + " " + requestType
                        + " should not be null",  propertyName);
                final List<Field> fields = Arrays.asList(ChangeStatusRequest.class.getDeclaredFields());
                Field result = null;
                for (Field field : fields) {
                    if (field.getName().equals(propertyName)){
                        result = field;
                        break;
                    }
                }
                assertNotNull("Property for " + type + " " + requestType
                        + " should exist", result);
            }
        }
    }
}
