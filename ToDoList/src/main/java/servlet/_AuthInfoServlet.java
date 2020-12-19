package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import servlet.abstracts.UserServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class _AuthInfoServlet extends UserServlet {

    /**
     * Метод получения авторизованного пользователя
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        returnJsonData(response, mapper.writeValueAsString(user));
    }

    /**
     * Метод завершения сессии пользователя
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        accountService.logoutUser(request.getSession().getId());
        returnJsonData(response, "Goodbye!");
    }
}
