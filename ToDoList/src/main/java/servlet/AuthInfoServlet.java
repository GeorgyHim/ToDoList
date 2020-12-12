package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.base.UserServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInfoServlet extends UserServlet {

    /** Сериализатор в Json */
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Метод получения авторизованного пользователя
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        returnData(response, mapper.writeValueAsString(user));
    }

    /**
     * Метод завершения сессии пользователя
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        accountService.logoutUser(request.getSession().getId());
        returnData(response, "Goodbye!");
    }
}
