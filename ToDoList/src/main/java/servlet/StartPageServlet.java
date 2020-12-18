package servlet;

import model.User;
import service.AccountService;
import servlet.abstracts.AccountServlet;
import util.exception.UserNotAuthorized;
import util.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class StartPageServlet extends AccountServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionId = req.getSession().getId();
        try {
            User user = AccountService.getInstance().getAuthorizedUser(sessionId);
            resp.sendRedirect("/tasks/all");
            return;
        } catch (UserNotAuthorized ignored) {}
        super.service(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        setHtmlContent(resp);
        resp.getWriter().println(PageGenerator.getInstance().renderPage("startPage.html", Collections.emptyMap()));
    }
}
