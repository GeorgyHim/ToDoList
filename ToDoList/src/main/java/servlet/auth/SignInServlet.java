package servlet.auth;

import model.User;
import servlet.abstracts.AccountServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignInServlet extends AccountServlet {

    /**
     * Метод авторизации пользователя
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = Optional.ofNullable(request.getParameter("email")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        User user = accountService.getUserByLogin(email);
        if (user == null || !user.getPassword().equals(password)) {
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.loginUser(request.getSession().getId(), user);
        returnJsonData(response, "Authorized: " + email);
    }
}
