package interlayer.dao;

import model.Task;

public class TaskDAO extends DAO<Task> {
    private static TaskDAO taskDAO = new TaskDAO();

    private TaskDAO() {
        super(Task.class);
    }

    public static TaskDAO getInstance() {
        return taskDAO;
    }
}
