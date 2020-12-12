package servlet.base;

import service.AccountService;

public abstract class AccountServlet extends BaseServlet {

    /** Ссылка на {@link service.AccountService} */
    protected final AccountService accountService;

    public AccountServlet(AccountService accountService) {
        this.accountService = accountService;
    }

}
