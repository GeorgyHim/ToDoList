package servlet.profile;

import interlayer.dao.UserDAO;
import model.Updater;
import model.User;
import service.AccountService;
import servlet.abstracts.UserServlet;
import util.exception.UserNotAuthorized;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends UserServlet {

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            User user = AccountService.getInstance().getAuthorizedUser(req.getSession().getId());
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String password = req.getParameter("password");
            Updater.updateUser(user, name, surname, password);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserNotAuthorized userNotAuthorized) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String sessionId = req.getSession().getId();
            User user = accountService.getAuthorizedUser(sessionId);
            accountService.logoutUser(sessionId);
            UserDAO.getInstance().delete(user);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (UserNotAuthorized userNotAuthorized) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
