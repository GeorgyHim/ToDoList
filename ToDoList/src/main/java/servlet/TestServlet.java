package servlet;

import com.google.gson.Gson;
import interlayer.dao.UserDAO;
import model.Creator;
import model.Task;
import model.ToDoList;
import model.User;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;

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
        try {
            User user = Creator.createUser("sa231");
            ToDoList list = Creator.createToDoList("Common things", user);
            Task task = Creator.createTask("Some work", list);
            Map<String, Object> data = new HashMap<>();
            data.put("user", UserDAO.getInstance().getByField("email", "atingo"));
            returnData(response, gson.toJson(data));
        } catch (UserAlreadyRegistered | ValidationError userAlreadyRegistered) {
            // TODO: Пока так. Вообще будет возвращаться 400 ошибка
            userAlreadyRegistered.printStackTrace();
        }
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
