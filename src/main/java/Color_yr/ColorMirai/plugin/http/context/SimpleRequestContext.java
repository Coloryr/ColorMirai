package Color_yr.ColorMirai.plugin.http.context;

import org.apache.commons.fileupload.RequestContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class SimpleRequestContext implements RequestContext {
    private final Charset charset;            // 编码
    private final InputStream content;        // 数据
    private final String contentType;

    public SimpleRequestContext(Charset charset, InputStream content, String contentType) {
        this.charset = charset;
        this.content = content;
        this.contentType = contentType;
    }

    @Override
    public String getCharacterEncoding() {
        return this.charset.displayName();
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public int getContentLength() {
        try {
            return this.content.available();
        } catch (IOException e) {
        }
        return 0;
    }

    @Override
    public InputStream getInputStream() {
        return this.content;
    }
}