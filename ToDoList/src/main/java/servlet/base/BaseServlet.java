package servlet.base;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
     * Запись объекта user в response в формате json
     */
    protected void returnData(HttpServletResponse response, String data) throws IOException {
        response.getWriter().println(data);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
