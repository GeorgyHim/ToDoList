package interlayer.dao;

import model.ToDoList;

/**
 * DAO для объектов {@link ToDoList}
 */
public class ToDoListDAO extends DAO<ToDoList> {
    private static ToDoListDAO toDoListDAO = new ToDoListDAO();

    private ToDoListDAO() {
        super(ToDoList.class);
    }

    public static ToDoListDAO getInstance() {
        return toDoListDAO;
    }
}
