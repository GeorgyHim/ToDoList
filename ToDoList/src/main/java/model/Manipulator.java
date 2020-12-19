package model;

import interlayer.dao.TaskDAO;
import interlayer.dao.ToDoListDAO;
import interlayer.dao.UserDAO;
import util.exception.UserAlreadyRegistered;
import util.exception.ValidationError;

import java.util.Optional;

public class Manipulator {
    private static UserDAO userDAO = UserDAO.getInstance();
    private static ToDoListDAO toDoListDAO = ToDoListDAO.getInstance();
    private static TaskDAO taskDAO = TaskDAO.getInstance();

    /**
     * Объект для создания и сохранения объектов
     */
    public static class Creator {

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
            createToDoList(ToDoList.defaultTitle, user);
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
    //------------------------------------------------------------------------------------------------------


    public static class Updater {

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
    }
}