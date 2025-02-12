package tcmapp.servlet;

import tcmapp.service.UserService;
import tcmapp.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Dashboard extends HttpServlet {
    private final UserService userService = new UserService();

    // GET - Fetch all users
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Ok>>>>>>>>>>>>>>>>>>>>>");
        List<User> users = userService.getAllUsers();
        resp.setContentType("application/json");
        resp.getWriter().write(users.toString()); // Convert to JSON if needed
    }

    // POST - Create a new user
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        userService.addUser(name, email);
        resp.getWriter().write("User added successfully");
    }

    // PUT - Update user
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        userService.updateUser(id, name, email);
        resp.getWriter().write("User updated successfully");
    }

    // DELETE - Remove user
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        userService.deleteUser(id);
        resp.getWriter().write("User deleted successfully");
    }
}
