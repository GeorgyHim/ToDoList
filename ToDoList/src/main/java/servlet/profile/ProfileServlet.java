package servlet.profile;

import interlayer.dao.UserDAO;
import model.Manipulator;
import servlet.abstracts.UserServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileServlet extends UserServlet {

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String password = req.getParameter("password");
        Manipulator.updateUser(this.user, name, surname, password);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String sessionId = req.getSession().getId();
        accountService.logoutUser(sessionId);
        UserDAO.getInstance().delete(this.user);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
