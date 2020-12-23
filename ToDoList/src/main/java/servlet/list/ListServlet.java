package servlet.list;

import interlayer.dao.ToDoListDAO;
import model.Manipulator;
import model.ToDoList;
import servlet.abstracts.UserServlet;
import util.exception.ExceptionHandler;
import util.exception.ValidationError;
import util.templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

        Map<String, Object> data = new HashMap<>();
        data.put("todolist", toDoList.get());
        data.put("user", this.user);

        setHtmlContent(resp);
        resp.getWriter().println(PageGenerator.getInstance().renderPage("list.html", data));
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        if (title.equals(ToDoList.defaultTitle)) {
            ExceptionHandler.handleException(new ValidationError("Нельзя удалить список по умолчанию"), resp);
            return;
        }

        Optional<ToDoList> toDoList =
                this.user.getToDoLists().stream().filter(list -> list.getTitle().equals(title)).findFirst();
        if (!toDoList.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        ToDoListDAO.getInstance().delete(toDoList.get());
        user.getToDoLists().remove(toDoList.get());
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String title = req.getParameter("title");
        Optional<ToDoList> toDoList =
                this.user.getToDoLists().stream().filter(list -> list.getTitle().equals(title)).findFirst();
        if (!toDoList.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String newTitle = req.getParameter("newTitle");
        if (title.equals(newTitle)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        toDoList.get().setTitle(newTitle);
        ToDoListDAO.getInstance().update(toDoList.get());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
