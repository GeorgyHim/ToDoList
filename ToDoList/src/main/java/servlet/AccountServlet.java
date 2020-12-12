package servlet;

import service.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AccountServlet extends BaseServlet {

    /** Ссылка на {@link service.AccountService} */
    protected final AccountService accountService;

    public AccountServlet(AccountService accountService) {
        this.accountService = accountService;
    }

}
