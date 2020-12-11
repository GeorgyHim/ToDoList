package service;

import model.Task;
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

    /**
     * Метод добавления дела в список
     * @param task      -   дело
     * @param toDoList  -   список дел
     */
    public static void addTaskToList(Task task, ToDoList toDoList) {
        toDoList.getTasks().add(task);
    }
}
