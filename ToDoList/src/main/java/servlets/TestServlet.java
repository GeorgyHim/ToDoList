package servlets;

import com.google.gson.Gson;
import models.user.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TestServlet extends HttpServlet {

    /** Сериализатор в Json */
    private static Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("user", new User("sambady"));
        data.put("time", LocalDate.now());
        returnData(response, gson.toJson(data));

    }

    /**
     * Метод записи данных в response в формате json
     */
    private void returnData(HttpServletResponse response, String data) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(data);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
