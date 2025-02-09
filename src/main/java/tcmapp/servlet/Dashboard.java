package tcmapp.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ws.rs.core.MediaType;
import tcmapp.service.TestCaseService;
import com.atlassian.sal.api.component.ComponentLocator;
import tcmapp.database.TestCaseDAO;

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
            String name = req.getParameter("name");
            String description = req.getParameter("description");

            if (name == null || description == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Missing parameters\"}");
                return;
            }

            testCaseDAO.createTestCase(name, description);
            resp.setContentType(MediaType.APPLICATION_JSON);
            resp.getWriter().write("{\"message\": \"Test case created successfully\"}");
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"An internal error occurred: " + e.getMessage() + "\"}");
        }
    }
}
