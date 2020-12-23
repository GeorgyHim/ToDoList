package servlet.tasks;

import model.Task;
import model.ToDoList;
import model.helper.DateGroup;
import servlet.abstracts.UserServlet;
import util.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TasksServlet extends UserServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> data = new HashMap<>();
        List<Task> tasks = getFilteredTasks(req.getParameter("filter"));
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

        if (filter.equals("today"))
            tasks = tasks.stream().filter(task -> task.getDateGroup() == DateGroup.TODAY).collect(Collectors.toList());

        if (filter.equals("soon"))
            tasks = tasks.stream().filter(
                    task ->
                            task.getDateGroup() == DateGroup.TODAY ||
                            task.getDateGroup() == DateGroup.TOMORROW ||
                            task.getDateGroup() == DateGroup.UPCOMING
            ).collect(Collectors.toList());

        tasks.sort(Comparator.nullsLast(Comparator.comparing(Task::getDateGroup))
                .thenComparing(Comparator.comparing(Task::getOrderNumber).reversed()));
        return tasks;
    }
}
