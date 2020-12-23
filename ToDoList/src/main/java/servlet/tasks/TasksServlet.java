package servlet.tasks;

import model.Manipulator;
import model.Task;
import model.ToDoList;
import model.helper.DateGroup;
import servlet.abstracts.UserServlet;
import util.exception.ExceptionHandler;
import util.exception.ObjectNotFound;
import util.exception.ValidationError;
import util.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class TasksServlet extends UserServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");

        Optional<ToDoList> toDoList =
                this.user.getToDoLists().stream().filter(tdList -> tdList.getTitle().equals(title)).findFirst();
        if (!toDoList.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            Manipulator.createTask(description, toDoList.get());
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.sendRedirect("/");
        } catch (ValidationError e) {
            ExceptionHandler.handleException(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String descr = req.getParameter("description");
        String order = req.getParameter("order");
        String completed = req.getParameter("completed");

        try {
            Manipulator.updateTask(Long.parseLong(id), descr, Integer.parseInt(order), Boolean.parseBoolean(completed));
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleException(new ValidationError("Неверный формат параметров"), resp);
        } catch (ObjectNotFound e) {
            ExceptionHandler.handleException(e, resp);
        }
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

        if (Objects.equals(filter, "today"))
            tasks = tasks.stream().filter(task -> task.getDateGroup() == DateGroup.TODAY).collect(Collectors.toList());

        if (Objects.equals(filter, "soon"))
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
