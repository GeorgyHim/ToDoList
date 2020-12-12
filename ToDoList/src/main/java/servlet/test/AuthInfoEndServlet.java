package servlet.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import servlet.base.UserServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInfoEndServlet extends UserServlet {

    /** Сериализатор в Json */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Метод получения авторизованного пользователя
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (this.user == null) {
            response.getWriter().println("User not authorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        returnData(response, mapper.writeValueAsString(user));
    }

    /**
     * Метод завершения сессии пользователя
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (this.user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        accountService.logoutUser(request.getSession().getId());
        returnData(response, "Goodbye!");
    }
}
