package servlet.profile;

import interlayer.dao.UserDAO;
import model.User;
import org.eclipse.jetty.server.Authentication;
import servlet.abstracts.UserServlet;
import util.exception.UserNotAuthorized;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends UserServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String sessionId = req.getSession().getId();
            User user = accountService.getAuthorizedUser(sessionId);
            accountService.logoutUser(sessionId);
            UserDAO.getInstance().delete(user);
        } catch (UserNotAuthorized ignored) {}
    }
}
