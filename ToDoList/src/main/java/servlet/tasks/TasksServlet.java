package servlet.tasks;

import interlayer.dao.TaskDAO;
import model.Manipulator;
import model.Task;
import model.ToDoList;
import model.helper.DateGroup;
import servlet.abstracts.UserServlet;
import util.exception.ExceptionHandler;
import util.exception.ObjectNotFound;
import util.exception.ValidationError;
import util.templater.PageGenerator;

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
        Long task_id;
        try {
             task_id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleException(new ValidationError("Неверный формат id"), resp);
            return;
        }

        Task task = TaskDAO.getInstance().getById(task_id);
        if (task == null || !task.getList().getUser().getEmail().equals(this.user.getEmail())) {
            ExceptionHandler.handleException(new ObjectNotFound("Указанной задачи не существует"), resp);
            return;
        }

        String descr = req.getParameter("description");
        String order = req.getParameter("order");
        String completed = req.getParameter("completed");

        Integer orderNum;
        try {
            orderNum = (order == null) ? null : Integer.parseInt(order);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleException(new ValidationError("Неверный формат order"), resp);
            return;
        }

        try {
            Manipulator.updateTask(task, descr, orderNum, completed);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleException(new ValidationError("Неверный формат параметров"), resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Long task_id;
        try {
            task_id = Long.parseLong(id);
        } catch (NumberFormatException e) {
            ExceptionHandler.handleException(new ValidationError("Неверный формат id"), resp);
            return;
        }

        Task task = TaskDAO.getInstance().getById(task_id);
        if (task == null || !task.getList().getUser().getEmail().equals(this.user.getEmail())) {
            ExceptionHandler.handleException(new ObjectNotFound("Указанной задачи не существует"), resp);
            return;
        }

        task.getList().getTasks().remove(task);
        TaskDAO.getInstance().delete(task);
        resp.setStatus(HttpServletResponse.SC_OK);
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
