package Color_yr.ColorMirai.plugin.http.HttpContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HttpAuth implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        String response = "Welcome Real's HowTo test page";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
