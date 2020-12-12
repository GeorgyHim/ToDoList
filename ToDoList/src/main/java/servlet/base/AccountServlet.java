package servlet.base;

import service.AccountService;

public abstract class AccountServlet extends BaseServlet {
    /** Сервис управления аккаунтами пользователей */
    protected AccountService accountService = AccountService.getInstance();
}
