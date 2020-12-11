package model;

import interlayer.dao.TaskDAO;
import interlayer.dao.ToDoListDAO;
import interlayer.dao.UserDAO;
import model.User;
import util.exception.ValidationError;

/**
 * Объект для создания и сохранения объектов
 */
public class Creator {
    private static UserDAO userDAO = UserDAO.getInstance();
    private static ToDoListDAO toDoListDAO = ToDoListDAO.getInstance();
    private static TaskDAO taskDAO = TaskDAO.getInstance();

    public static User createUser(String email) {
        User user = new User(email);
        userDAO.add(user);
        return user;
    }

    public static ToDoList createToDoList(String title, User user) throws ValidationError {
        ToDoList toDoList = new ToDoList(title, user);
        toDoListDAO.add(toDoList);
        addListToItsUser(toDoList);
        return toDoList;
    }


    /**
     * Метод добавления списка дел пользователю
     * @param list  -   список дел
     */
    private static void addListToItsUser(ToDoList list) {
        list.getUser().getToDoLists().add(list);
        userDAO.update(list.getUser());
    }

    /**
     * Метод добавления дела в список
     * @param task      -   дело
     */
    private static void addTaskToItsList(Task task) {
        task.getList().getTasks().add(task);
        toDoListDAO.update(task.getList());
    }
}
