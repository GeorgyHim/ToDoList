package util.exception;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandler {
    /**
     * Метод обработки исключений, возникающих в сервлетах
     *
     * @param e             -   Исключение
     * @param response      -   Респонс сервлета
     * @throws IOException  -   Исключение во время записи сообщения в ресонс
     */
    public static void handleException(Exception e, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");

        if (e instanceof UserAlreadyRegistered) {
            response.getWriter().println("User with specified email already exist");
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            return;
        }

        if (e instanceof ValidationError) {
            response.getWriter().println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (e instanceof UserNotAuthorized) {
            response.sendRedirect("/");
            return;
        }

        if (e instanceof ObjectNotFound) {
            response.getWriter().println(e.getMessage());
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        e.printStackTrace();
        response.getWriter().println(e.getMessage());
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
