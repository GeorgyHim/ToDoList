package servlet;

import model.User;
import service.AccountService;
import util.exception.UserAlreadyAuthorized;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends AccountServlet {

    public SignInServlet(AccountService accountService) {
        super(accountService);
    }

    /**
     * Метод авторизации пользователя
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setContentType(response);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        User user = accountService.getUserByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            accountService.loginUser(request.getSession().getId(), user);
        } catch (UserAlreadyAuthorized userAlreadyAuthorized) {
            response.getWriter().println("User already authorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        returnData(response, "Authorized: " + login);
    }
}
