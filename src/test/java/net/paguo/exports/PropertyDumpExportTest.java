package net.paguo.exports;

import org.junit.Test;
import org.apache.commons.beanutils.PropertyUtils;
import net.paguo.domain.requests.ChangeStatusRequest;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.text.ChoiceFormat;
import java.text.NumberFormat;

/**
 * @author Reyentenko
 */
public class PropertyDumpExportTest {
    @Test
    public final void testBooleanDump() throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ChangeStatusRequest csr = new ChangeStatusRequest();
        csr.setPermanent(true);
        final Object o = PropertyUtils.getProperty(csr, "numericalType");
        NumberFormat t = new ChoiceFormat("0#permanent | 0<restorable");
        final String s = t.format(o);
        System.out.println(s);
    }
}
