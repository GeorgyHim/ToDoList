package servlet.list;

import model.Manipulator;
import model.ToDoList;
import servlet.abstracts.UserServlet;
import util.exception.ExceptionHandler;
import util.exception.ValidationError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ListServlet extends UserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        Optional<ToDoList> toDoList =
                this.user.getToDoLists().stream().filter(list -> list.getTitle().equals(title)).findFirst();
        if (!toDoList.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // TODO: Поработать над представлением данных
        returnJsonData(resp, mapper.writeValueAsString(toDoList.get()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        try {
            Manipulator.createToDoList(title, this.user);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (ValidationError validationError) {
            ExceptionHandler.handleException(validationError, resp);
        }
    }
}
