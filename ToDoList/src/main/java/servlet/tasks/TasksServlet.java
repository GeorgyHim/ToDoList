package servlet.tasks;

import model.Task;
import model.ToDoList;
import servlet.abstracts.UserServlet;
import util.templater.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class TasksServlet extends UserServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Task> tasks = getFilteredTasks(req.getParameter("filter"));

        Map<String, Object> data = new HashMap<>();
        data.put("taskList", tasks);
        data.put("user", this.user);

        setHtmlContent(resp);
        resp.getWriter().println(PageGenerator.getInstance().renderPage("tasks.html", data));
    }

    /**
     * Метод фильтрации задач
     *
     * @param filter    -   Фильтр
     * @return          -   Список соответствующих задач
     */
    private List<Task> getFilteredTasks(String filter) {
        List<Task> tasks = new ArrayList<>();
        for (ToDoList toDoList : this.user.getToDoLists()) {
            tasks.addAll(toDoList.getTasks());
        }
        tasks.sort(Comparator.comparing(Task::getOrderNumber).reversed());

        if (filter == null || filter.isEmpty() || filter.equals("all"))
            return tasks;

        // TODO: Фильтрация для today и week

        return tasks;
    }
}
