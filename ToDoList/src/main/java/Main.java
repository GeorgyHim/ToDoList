import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.TestServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = createServer(8080);
        server.start();
        System.out.println("Server started");
        server.join();
    }

    private static Server createServer(int port) {
        TestServlet testServlet = new TestServlet();
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.addServlet(new ServletHolder(testServlet), "/test");
        Server server = new Server(port);
        server.setHandler(handler);
        return server;
    }
}
