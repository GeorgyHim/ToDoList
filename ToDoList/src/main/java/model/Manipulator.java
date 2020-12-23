package model;

import interlayer.dao.TaskDAO;
import interlayer.dao.ToDoListDAO;
import interlayer.dao.UserDAO;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;

import java.util.Optional;

/**
 * Класс для создания, изменения и сохранения объектов в БД
 */
public class Manipulator {
    private static UserDAO userDAO = UserDAO.getInstance();
    private static ToDoListDAO toDoListDAO = ToDoListDAO.getInstance();
    private static TaskDAO taskDAO = TaskDAO.getInstance();

    // ----- UPDATE methods -----
    public static void updateUser(User user, String newName, String newSurname, String newPassword) {
        newName = Optional.ofNullable(newName).orElse("");
        newSurname = Optional.ofNullable(newSurname).orElse("");
        newPassword = Optional.ofNullable(newPassword).orElse("");

        if (!newName.isEmpty())
            user.setName(newName);
        if (!newSurname.isEmpty())
            user.setSurname(newSurname);
        if (!newPassword.isEmpty())
            user.setPassword(newPassword);

        userDAO.update(user);
    }

    public static void updateToDoList(ToDoList toDoList, String newTitle) throws ValidationError {
        String oldTitle = toDoList.getTitle();
        String nTitle = Optional.ofNullable(newTitle).orElse("");
        if (toDoList.getTitle().equals(newTitle)) {
            return;
        }
        if (!nTitle.isEmpty() &&
                toDoList.getUser().getToDoLists().stream().noneMatch(tdList -> tdList.getTitle().equals(nTitle))) {
            toDoList.setTitle(newTitle);
            toDoListDAO.update(toDoList);
            if (oldTitle.equals(toDoList.getUser().getPersonalDefaultListTitle())) {
                toDoList.getUser().setPersonalDefaultListTitle(nTitle);
                userDAO.update(toDoList.getUser());
            }
        }
        else
            if (nTitle.isEmpty())
                throw new ValidationError("Название списка не может быть пустым!");
            else
                throw new ValidationError("Список с указанным названием уже существует!");
    }
    //----------------------------------------------------------------------------------------------------

    // ----- CREATE methods -----
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
    //----------------------------------------------------------------------------------------------------

    // ----- ADD methods -----
    /**
     * Метод добавления объекта {@link User} в БД
     *
     * @param user                      -   пользователь
     * @throws UserAlreadyRegistered    -   Исключение когда пользователь уже существует
     * @throws ValidationError          -   Исключение при некорректных данных
     */
    public static void addUserToDB(User user) throws UserAlreadyRegistered, ValidationError {
        if (userDAO.getByField("email", user.getEmail()) != null)
            throw new UserAlreadyRegistered();
        userDAO.add(user);
        createToDoList(user.getPersonalDefaultListTitle(), user);
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
