package interlayer.dao;

import model.User;

public class UserDAO extends DAO<User> {
    private static UserDAO userDAO = new UserDAO();

    private UserDAO() {
        super(User.class);
    }

    public static UserDAO getInstance() {
        return userDAO;
    }
}
