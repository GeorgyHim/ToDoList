package service;

import interlayer.dao.DAO;
import model.User;
import util.exception.UserAlreadyAuthorized;
import util.exception.UserAlreadyRegistered;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для управления аккаунтами пользователей
 */
public class AccountService {

    private final DAO<User> userDAO;

    /** Перечень авторизованных пользователей со связью sessionId-{@link User} */
    private final Map<String, User> authorizedUsers;

    public AccountService() {
        userDAO = new DAO<>(User.class);
        this.authorizedUsers = new HashMap<>();
    }

//    /**
//     * Метод регистрации нового пользователя
//     *
//     * @param newUser                   - Новый пользователь
//     * @throws UserAlreadyRegistered    - Исключение в ситуации когда указанный логин уже занят
//     */
//    public void registerNewUser(User newUser) throws UserAlreadyRegistered {
//        if (userDAO.getByLogin(newUser.getLogin()) != null)
//            throw new UserAlreadyRegistered();
//
//        userDAO.addUser(newUser);
//    }

//    /**
//     * Метод получения пользователя по его логину
//     *
//     * @param login - Логин пользователя
//     * @return      - Найденный пользователь либо Null
//     */
//    public User getUserByLogin(String login) {
//        return userDAO.getByLogin(login);
//    }

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
     * @throws UserAlreadyAuthorized    - Исключение когда указанная сессия уже связана с другим пользователем
     */
    public void loginUser(String sessionId, User user) throws UserAlreadyAuthorized {
        if (authorizedUsers.containsKey(sessionId) && !authorizedUsers.get(sessionId).equals(user))
            throw new UserAlreadyAuthorized();

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
