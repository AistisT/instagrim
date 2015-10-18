package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    Cluster cluster = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if ((String) session.getAttribute("Username") != null) {
            response.sendRedirect("Home");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            User us = new User();
            us.setCluster(cluster);
            boolean isValid = us.IsValidUser(username, password);
            System.out.println("Session in servlet " + session);
            if (isValid) {
                session.setAttribute("Username", username);
                response.sendRedirect("Home");
            } else {
                RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                request.setAttribute("fail", true);
                rd.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if ((String) session.getAttribute("Username") == null) {
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("Home");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
