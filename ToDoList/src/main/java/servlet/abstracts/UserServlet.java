package servlet.abstracts;

import interlayer.dao.UserDAO;
import model.User;
import util.exception.ExceptionHandler;
import util.exception.UserNotAuthorized;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Абстрактный сервлет для случаев, когда пользователь уже должен быть зарегистрирован
 */
public abstract class UserServlet extends AccountServlet {
    /** Авторизованный пользователь */
    protected User user;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        try {
            long user_id = accountService.getAuthorizedUser(sessionId).getId();
            this.user = UserDAO.getInstance().getById(user_id);
            super.service(req, resp);
        } catch (UserNotAuthorized userNotAuthorized) {
            ExceptionHandler.handleException(userNotAuthorized, resp);
        }
    }
}
