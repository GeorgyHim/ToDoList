package servlet.auth;

import model.Manipulator;
import servlet.abstracts.BaseServlet;
import util.exception.ExceptionHandler;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;
import util.templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class SignUpServlet extends BaseServlet {

    /**
     * Отображение страницы с формой для регистрации
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setHtmlContent(resp);
        resp.getWriter().println(PageGenerator.getInstance().renderPage("signup.html", Collections.emptyMap()));
    }

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
            Manipulator.createUser(email, password, name, surname);
            returnJsonData(response, String.format("User %s registered", email));
        } catch (UserAlreadyRegistered | ValidationError e) {
            ExceptionHandler.handleException(e, response);
        }
    }
}
