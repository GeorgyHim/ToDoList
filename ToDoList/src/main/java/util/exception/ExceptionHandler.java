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

        e.printStackTrace();
    }
}
