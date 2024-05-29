package cn.bravedawn.config.mvc.intercepter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/5/29 14:18
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private  byte[] cachedBody;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedBody = StreamUtils.copyToByteArray(request.getInputStream());
    }


    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream bais = new ByteArrayInputStream(cachedBody);
        return new ServletInputStream() {

            /**
             * 如果已经读取了流中的所有数据，isFinish ()方法将返回 true，否则它将返回 false。
             */
            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }


            /**
             * 如果可以在不阻塞的情况下读取流中的数据，isReady ()方法将返回 true，否则它将返回 false。
             * 因为我们已经在一个字节数组中复制了 InputStream，所以我们将返回 true 来表示它始终可用:
             */
            @Override
            public boolean isReady() {
                return true;
            }

            /**
             * SetReadListener 方法用于指示 ServletInputStream在可以读取时调用提供的 ReadListener。
             */
            @Override
            public void setReadListener(ReadListener readListener) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }


    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }
}
