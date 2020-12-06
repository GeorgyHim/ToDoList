package servlet;

import service.AccountService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AccountServlet extends HttpServlet {

    /** Ссылка на {@link service.AccountService} */
    protected final AccountService accountService;

    public AccountServlet(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * Метод установления нужного типа контента для response
     */
    protected void setContentType(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
    }

    /**
     * Запись объекта user в response в формате json
     */
    protected void returnData(HttpServletResponse response, String data) throws IOException {
        response.getWriter().println(data);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
