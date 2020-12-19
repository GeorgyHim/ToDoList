package servlet._test;

import com.fasterxml.jackson.databind.ObjectMapper;
import interlayer.dao.UserDAO;
import model.Manipulator;
import model.User;
import servlet.abstracts.BaseServlet;
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
            String email = request.getParameter("email");
            User user = Manipulator.createUser(email);
            //ToDoList list = Creator.createToDoList("Common things", user);
            Manipulator.createTask("Some work", user.getToDoLists().stream().findAny().get());
            Map<String, Object> data = new HashMap<>();
            data.put("user", UserDAO.getInstance().getByField("email", email));
            returnJsonData(response, mapper.writeValueAsString(data));
        } catch (UserAlreadyRegistered | ValidationError e) {
            ExceptionHandler.handleException(e, response);
        }
    }
}
