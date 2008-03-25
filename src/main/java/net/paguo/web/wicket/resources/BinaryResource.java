package net.paguo.web.wicket.resources;

import org.apache.wicket.markup.html.DynamicWebResource;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.util.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Reyentenko
 */
public abstract class BinaryResource extends DynamicWebResource {
    private String name;
    private String contentType;
    private static final long serialVersionUID = -8744733173547782658L;
    protected static final String APPLICATION_VND_MS_EXCEL = "application/vnd.ms-excel";
    protected static final String APPLICATION_PDF = "application/pdf";

    public BinaryResource(String resourceName, String type) {
        this.name = resourceName;
        this.contentType = type;
    }

    protected void setHeaders(WebResponse response) {
        super.setHeaders(response);
        response.setHeader("Content-Disposition", "attachment; filename=" + name);
    }

    protected abstract InputStream getResource() throws IOException;

    private static class BinaryResourceState extends ResourceState {
        private byte[] stream;
        private String contentType;

        public BinaryResourceState(byte[] data, String s) {
            stream = data;
            this.contentType = s;
        }

        public byte[] getData() {
            return stream;
        }

        public int getLength() {
            return stream.length;
        }

        public String getContentType() {
            return this.contentType;
        }
    }

    protected ResourceState getResourceState() {
        System.out.println("getResourceState called");
        try {
            InputStream in = getResource();
            return new BinaryResourceState(IOUtils.toByteArray(in), contentType);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

