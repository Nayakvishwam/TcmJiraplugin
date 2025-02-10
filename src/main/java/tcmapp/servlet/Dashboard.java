package tcmapp.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import static com.google.common.base.Preconditions.*;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.sal.api.transaction.TransactionCallback;

import tcmapp.ao.*;

public class Dashboard extends BaseServlet {

    private ActiveObjects activeObjects;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String html = concateTemplate("templates/dashboard.html");
        sendHtmlResponse(resp, html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            // Ensure ActiveObjects is available
            activeObjects = ComponentAccessor.getOSGiComponentInstanceOfType(ActiveObjects.class);
            if (activeObjects == null) {
                System.out.println("Ok");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter()
                        .write("{\"error\": \"ActiveObjects is not initialized yet. Please try again later.\"}");
                return;
            }

            String name = req.getParameter("name");
            String description = req.getParameter("description");

            if (name == null || description == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Missing parameters\"}");
                return;
            }

            // Perform the transaction
            activeObjects.executeInTransaction(new TransactionCallback<Todo>() {
                @Override
                public Todo doInTransaction() {
                    final Todo todo = activeObjects.create(Todo.class);
                    todo.setDescription(description);
                    todo.setComplete(false);
                    todo.save();
                    return todo;
                }
            });

            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.getWriter().write("{\"message\": \"Test case created successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"An internal error occurred: " + e.getMessage() + "\"}");
        }
    }

}
