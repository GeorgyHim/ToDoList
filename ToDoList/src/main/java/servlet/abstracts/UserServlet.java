package servlet.abstracts;

import model.User;
import service.AccountService;
import util.exception.ExceptionHandler;
import util.exception.UserNotAuthorized;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Абстрактный сервлет для случаев, когда пользователь уже должен быть зарегистрирован
 */
public abstract class UserServlet  extends AccountServlet {
    /** Авторизованный пользователь */
    protected User user;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        try {
            this.user = AccountService.getInstance().getAuthorizedUser(sessionId);
            super.service(req, resp);
        } catch (UserNotAuthorized userNotAuthorized) {
            ExceptionHandler.handleException(userNotAuthorized, resp);
        }
    }
}
