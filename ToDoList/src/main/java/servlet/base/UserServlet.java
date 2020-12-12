package servlet.base;

import model.User;
import service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet  extends AccountServlet{
    /** Авторизованный пользователь */
    protected User user;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        this.user = AccountService.getInstance().getAuthorizedUser(sessionId);
        super.service(req, resp);
    }
}
