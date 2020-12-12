package servlet;

import model.Creator;
import service.AccountService;
import servlet.base.AccountServlet;
import util.exception.UserAlreadyRegistered;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class SignUpServlet extends AccountServlet {

    public SignUpServlet(AccountService accountService) {
        super(accountService);
    }

    /**
     * Метод регистрации пользователя
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setHtmlContent(response);

        String email = Optional.ofNullable(request.getParameter("email")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        if (email.isEmpty() || password.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            Creator.createUser(email, password, name, surname);
            response.getWriter().println(String.format("User %s registered", email));
        } catch (UserAlreadyRegistered e) {
            response.getWriter().println(String.format("User %s already exist", email));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
