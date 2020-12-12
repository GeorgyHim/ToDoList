package servlet.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import interlayer.dao.UserDAO;
import model.Creator;
import model.ToDoList;
import model.User;
import servlet.base.BaseServlet;
import util.exception.ExceptionHandler;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestServlet extends BaseServlet {

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
}
