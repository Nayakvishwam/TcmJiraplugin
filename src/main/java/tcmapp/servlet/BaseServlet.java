package tcmapp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(BaseServlet.class.getName());

    protected String loadTemplate(String resourcePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                LOGGER.warning("Template not found: " + resourcePath);
                return "";
            }
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String content = scanner.useDelimiter("\\A").next();
            scanner.close();
            return content;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading template: " + resourcePath, e);
            return "";
        }
    }
    protected String concateTemplate(String copyTemplatePath){
        String indexhtml = loadTemplate("templates/index.html");
        String headerHtml = loadTemplate("templates/header.html");
        String sidebarHtml = loadTemplate("templates/sidebar.html");
        String copyTemplate=loadTemplate(copyTemplatePath);
        String mainHtml = indexhtml.replace("{{header}}", headerHtml)
                .replace("{{sideBar}}", sidebarHtml)
                .replace("{{copyTemplate}}", copyTemplate);
        return mainHtml;
    }
    protected void sendHtmlResponse(HttpServletResponse resp, String htmlContent) {
        try {
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write(htmlContent);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing response", e);
        }
    }
}
