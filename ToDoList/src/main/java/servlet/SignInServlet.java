package servlet;

import model.User;
import service.AccountService;
import servlet.base.AccountServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignInServlet extends AccountServlet {

    public SignInServlet(AccountService accountService) {
        super(accountService);
    }

    /**
     * Метод авторизации пользователя
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setHtmlContent(response);

        String email = Optional.ofNullable(request.getParameter("email")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        if (email.isEmpty() || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User user = accountService.getUserByLogin(email);
        if (user == null || !user.getPassword().equals(password)) {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.loginUser(request.getSession().getId(), user);
        returnData(response, "Authorized: " + email);
    }
}
