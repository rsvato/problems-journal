package net.paguo.exports;

import net.paguo.domain.requests.ChangeStatusRequest;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.lang.reflect.InvocationTargetException;
import java.text.ChoiceFormat;
import java.text.NumberFormat;
import java.util.Locale;

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
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("messages");
        final String message = ms.getMessage("permanentChoice", new Object[]{o}, new Locale("ru", "RU"));
        System.out.println(message);
    }
}
