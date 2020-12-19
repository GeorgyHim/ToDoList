package servlet.list;

import model.ToDoList;
import servlet.abstracts.UserServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class ListServlet extends UserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String title = req.getParameter("title");
        Optional<ToDoList> toDoList =
                this.user.getToDoLists().stream().filter(list -> list.getTitle().equals(title)).findFirst();
        if (!toDoList.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        returnJsonData(resp, mapper.writeValueAsString(toDoList.get()));
    }
}
