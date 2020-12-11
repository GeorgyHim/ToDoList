package service;

import model.ToDoList;
import model.User;

/**
 * Сервис управления списками задач и задачами
 */
public class ToDoService {

    /**
     * Метод добавления списка дел пользователю
     * @param list  -   список дел
     * @param user  -   пользователь
     */
    public static void addListToUser(ToDoList list, User user) {
        user.getToDoLists().add(list);
    }

    public static void addTaskToList() {}
}
