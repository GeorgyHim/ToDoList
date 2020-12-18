package servlet.profile;

import servlet.abstracts.UserServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends UserServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        accountService.logoutUser(req.getSession().getId());
        resp.sendRedirect("/");
    }
}
