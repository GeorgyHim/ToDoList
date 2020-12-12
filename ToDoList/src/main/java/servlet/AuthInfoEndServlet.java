package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;
import service.AccountService;
import servlet.base.AccountServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInfoEndServlet extends AccountServlet {

    /** Сериализатор в Json */
    private static ObjectMapper mapper = new ObjectMapper();

    public AuthInfoEndServlet(AccountService accountService) {
        super(accountService);
    }

    /**
     * Метод получения авторизованного пользователя
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        setHtmlContent(response);

        String sessionId = request.getSession().getId();
        User user = accountService.getAuthorizedUser(sessionId);
        if (user == null) {
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
        setHtmlContent(response);

        String sessionId = request.getSession().getId();
        User user = accountService.getAuthorizedUser(sessionId);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        accountService.logoutUser(sessionId);
        returnData(response, "Goodbye!");
    }
}
