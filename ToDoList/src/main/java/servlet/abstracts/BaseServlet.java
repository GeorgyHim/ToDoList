package servlet.abstracts;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Базовый сервлет с функциями записи данных в респонс
 */
public abstract class BaseServlet extends HttpServlet {
    /**
     * Метод установления контента типа HTML для response
     */
    protected void setHtmlContent(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
    }

    /**
     * Метод установления контента типа JSON для response
     */
    protected void setJsonContent(HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
    }

    /**
     * Метод записи в response данных в формате json
     */
    protected void returnJsonData(HttpServletResponse response, String jsonData) throws IOException {
        setJsonContent(response);
        response.getWriter().println(jsonData);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
