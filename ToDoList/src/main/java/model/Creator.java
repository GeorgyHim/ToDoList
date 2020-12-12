package model;

import interlayer.dao.TaskDAO;
import interlayer.dao.ToDoListDAO;
import interlayer.dao.UserDAO;
import model.User;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;

/**
 * Объект для создания и сохранения объектов
 */
public class Creator {
    private static UserDAO userDAO = UserDAO.getInstance();
    private static ToDoListDAO toDoListDAO = ToDoListDAO.getInstance();
    private static TaskDAO taskDAO = TaskDAO.getInstance();

    public static User createUser(String email) throws UserAlreadyRegistered, ValidationError {
        User user = new User(email);
        addUserToDB(user);
        return user;
    }

    public static User createUser(String email, String password) throws UserAlreadyRegistered, ValidationError {
        User user = new User(email, password);
        addUserToDB(user);
        return user;
    }

    public static User createUser(String email, String password, String name, String surname) throws UserAlreadyRegistered, ValidationError {
        User user = new User(email, password, name, surname);
        addUserToDB(user);
        return user;
    }

    public static void addUserToDB(User user) throws UserAlreadyRegistered {
        if (userDAO.getByField("email", user.getEmail()) != null)
            throw new UserAlreadyRegistered();
        userDAO.add(user);
    }

    public static ToDoList createToDoList(String title, User user) throws ValidationError {
        ToDoList toDoList = new ToDoList(title, user);
        toDoListDAO.add(toDoList);
        addListToItsUser(toDoList);
        return toDoList;
    }

    public static Task createTask(String description, ToDoList list) throws ValidationError {
        Task task = new Task(description, list);
        taskDAO.add(task);
        task.setOrderNumber((int) task.getId());
        TaskDAO.getInstance().update(task);
        return task;
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
