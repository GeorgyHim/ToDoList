package servlet.auth;

import model.Creator;
import servlet.base.BaseServlet;
import util.exception.ExceptionHandler;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends BaseServlet {

    // TODO: doGet для отображения форм регистрации

    /**
     * Метод регистрации пользователя
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        try {
            Creator.createUser(email, password, name, surname);
            returnJsonData(response, String.format("User %s registered", email));
        } catch (UserAlreadyRegistered | ValidationError e) {
            ExceptionHandler.handleException(e, response);
        }
    }
}
