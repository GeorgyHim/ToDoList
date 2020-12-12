package service;

import interlayer.dao.UserDAO;
import model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис управления аккаунтами пользователей
 */
public class AccountService {

    private static AccountService accountService = new AccountService();

    /** Объект {@link UserDAO} для модели {@link User}*/
    private final UserDAO userDAO;

    /** Перечень авторизованных пользователей со связью sessionId-{@link User} */
    private final Map<String, User> authorizedUsers;

    private AccountService() {
        userDAO = UserDAO.getInstance();
        this.authorizedUsers = new HashMap<>();
    }

    public static AccountService getInstance() {
        return accountService;
    }

    /**
     * Метод получения пользователя по его логину
     *
     * @param email - Email пользователя
     * @return      - Найденный пользователь либо Null
     */
    public User getUserByLogin(String email) {
        return userDAO.getByField("email", email);
    }

    /**
     * Метод получения авторизованного пользователя по его сессии
     *
     * @param sessionId - Идентификатор сессии пользователя
     * @return          - Найденный пользователь либо Null
     */
    public User getAuthorizedUser(String sessionId) {
        return  authorizedUsers.get(sessionId);
    }

    /**
     * Метод авторизации пользователя
     *
     * @param sessionId                 - Идентификатор сессии пользвателя
     * @param user                      - Пользователь
     */
    public void loginUser(String sessionId, User user) {
        authorizedUsers.put(sessionId, user);
    }

    /**
     *
     * @param sessionId - Метод завершения сессии
     */
    public void logoutUser(String sessionId) {
        authorizedUsers.remove(sessionId);
    }

}
