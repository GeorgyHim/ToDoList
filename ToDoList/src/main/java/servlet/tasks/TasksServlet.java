package servlet.tasks;

import model.Task;
import model.ToDoList;
import servlet.abstracts.UserServlet;
import util.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class TasksServlet extends UserServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

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

        if (filter == null || filter.isEmpty() || filter.equals("all"))
            return tasks;

        // TODO: Фильтрация для today и week


        // TODO: Отсортировать
        //tasks.sort(Comparator.nullsLast(Comparator.comparing(Task::getDtPlanned))
        //        .thenComparing(Comparator.comparing(Task::getOrderNumber).reversed()));
        return tasks;
    }
}
