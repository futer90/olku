import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(80);
        ServletContextHandler h = new ServletContextHandler();
        h.addServlet(TestServlet.class, "");
        h.addServlet(RestServlet.class, "/rest/*");
        h.setResourceBase("web");
        h.addServlet(DefaultServlet.class, "/");

        server.setHandler(h);
        server.start();

    }
}