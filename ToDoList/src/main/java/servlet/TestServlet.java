package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import interlayer.dao.UserDAO;
import model.Creator;
import model.Task;
import model.ToDoList;
import model.User;
import util.exception.ExceptionHandler;
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
    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String username = "sambady";
            User user = Creator.createUser(username);
            ToDoList list = Creator.createToDoList("Common things", user);
            Creator.createTask("Some work", list);
            Map<String, Object> data = new HashMap<>();
            data.put("user", UserDAO.getInstance().getByField("email", username));
            returnData(response, mapper.writeValueAsString(data));
        } catch (UserAlreadyRegistered | ValidationError e) {
            ExceptionHandler.handleException(e, response);
        }
    }

    /**
     * Метод записи данных в response
     */
    private void returnData(HttpServletResponse response, String data) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(data);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
