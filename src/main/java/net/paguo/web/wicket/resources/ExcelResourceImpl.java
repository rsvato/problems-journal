package net.paguo.web.wicket.resources;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * @author Reyentenko
 */
public class ExcelResourceImpl extends BinaryResource {
    private static final long serialVersionUID = -3106910267810512508L;

    public ExcelResourceImpl(String name){
        super(name, APPLICATION_VND_MS_EXCEL);
    }

    protected InputStream getResource() {
        //just stub for now
        return new ByteArrayInputStream(new byte[0]);
    }
}
