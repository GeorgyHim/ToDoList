import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.AccountService;
import servlet.AuthInfoServlet;
import servlet.StartPageServlet;
import servlet.auth.SignInServlet;
import servlet.auth.SignUpServlet;
import servlet.test.TestServlet;

public class Main {
    private static AccountService accountService = AccountService.getInstance();

    public static void main(String[] args) throws Exception {
        Server server = createServer(8080);
        server.start();
        System.out.println("Server started");
        server.join();
    }

    private static Server createServer(int port) {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new AuthInfoServlet()), "/api/auth/");
        contextHandler.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        contextHandler.addServlet(new ServletHolder(new SignUpServlet()), "/signup");
        contextHandler.addServlet(new ServletHolder(new TestServlet()), "/test");

        contextHandler.addServlet(new ServletHolder(new StartPageServlet()), "/");
        contextHandler.addServlet(new ServletHolder(new AuthInfoServlet()), "/tasks/all");
        Server server = new Server(port);
        server.setHandler(contextHandler);
        return server;
    }
}
