import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Main {

    private static final Log log = LogFactory.getLog(Main.class);

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", null);
        Tomcat.addServlet(ctx, "hello", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                resp.setContentType("text/plain");
                resp.getWriter().write("Hello, Embedded Tomcat!");
            }
        });
        ctx.addServletMappingDecoded("/hello", "hello");

        tomcat.start();
        log.info("Tomcat started successfully. Server is running. Try accessing http://localhost:8080/hello");
        tomcat.getServer().await();
    }

}