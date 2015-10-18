package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

public class Settings extends HttpServlet {

    Cluster cluster = null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("Username");
        if (username != null) {
            User user = new User();
            user.setCluster(cluster);
            ArrayList<String> info = user.getUserinfo(username);
            request.setAttribute("firstName", info.get(0));
            request.setAttribute("lastName", info.get(1));
            request.setAttribute("email", info.get(2));
            session.setAttribute("displayProfilePic", true);
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            java.util.LinkedList<Pic> lsPics = tm.getProfilePic(username);
            request.setAttribute("ProfilePics", lsPics);
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("settings.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("Login");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        User us = new User();
        us.setCluster(cluster);
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("Username");
        if (username != null) {
            session.setAttribute("displayProfilePic", true);
            us.updateDetails(username, firstName, lastName, email);
            response.sendRedirect("Settings");
        } else {
            response.sendRedirect("Login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
