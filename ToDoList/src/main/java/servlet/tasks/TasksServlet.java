package servlet.tasks;

import model.Task;
import model.ToDoList;
import servlet.abstracts.UserServlet;
import util.templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TasksServlet extends UserServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Task> tasks = new ArrayList<>();
        for (ToDoList toDoList : this.user.getToDoLists()) {
            tasks.addAll(toDoList.getTasks());
        }
        String jsonData = mapper.writeValueAsString(tasks);
        setHtmlContent(resp);
        resp.getWriter().println(
                PageGenerator.getInstance().renderPage("tasks.html", Collections.singletonMap("data", jsonData))
        );
    }
}
