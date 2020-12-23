import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import service.AccountService;
import servlet.StartPageServlet;
import servlet.auth.SignInServlet;
import servlet.auth.SignUpServlet;
import servlet.list.ListServlet;
import servlet.profile.LogoutServlet;
import servlet.profile.ProfileServlet;
import servlet.tasks.TasksServlet;

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
        setUrls(contextHandler);
        Server server = new Server(port);
        server.setHandler(contextHandler);
        return server;
    }

    private static void setUrls(ServletContextHandler contextHandler) {
        contextHandler.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        contextHandler.addServlet(new ServletHolder(new SignUpServlet()), "/signup");
        contextHandler.addServlet(new ServletHolder(new StartPageServlet()), "/");

        contextHandler.addServlet(new ServletHolder(new TasksServlet()), "/tasks");

        contextHandler.addServlet(new ServletHolder(new ListServlet()), "/list");

        contextHandler.addServlet(new ServletHolder(new LogoutServlet()), "/profile/logout");
        contextHandler.addServlet(new ServletHolder(new ProfileServlet()), "/profile");
    }

    // TODO: Создать интерфейс для удаления списка на странице списка
}
