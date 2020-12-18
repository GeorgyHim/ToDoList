package servlet.abstracts;

import service.AccountService;

/**
 * Абстрактный сервлет для работы с учетом и управлением аккаунтами
 */
public abstract class AccountServlet extends BaseServlet {
    /** Сервис управления аккаунтами пользователей */
    protected AccountService accountService = AccountService.getInstance();
}
