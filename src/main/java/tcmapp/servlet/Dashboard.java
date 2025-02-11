package tcmapp.servlet;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import tcmapp.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Scanned
public class Dashboard extends BaseServlet {
    private static final Logger log = LoggerFactory.getLogger(Dashboard.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String html = concateTemplate("templates/dashboard.html");
        sendHtmlResponse(resp, html);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String description = req.getParameter("description");
            if (description == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Missing parameters\"}");
                return;
            }

            // Use singleton service
            TodoService todoService = ServiceManager.getTodoService();
            todoService.add(description);

            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.getWriter().write("{\"message\": \"Test case created successfully\"}");
        } catch (Exception e) {
            log.error("Error in Dashboard POST request", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"An internal error occurred: " + e.getMessage() + "\"}");
        }
    }
}
