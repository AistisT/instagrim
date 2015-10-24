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
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;

public class Like extends HttpServlet {

    Cluster cluster = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("Username");
        String userName = (String) request.getParameter("userName");
        String destination = (String) request.getParameter("source");
        System.out.println("username= " + userName);
        java.util.UUID picid = java.util.UUID.fromString(request.getParameter("picid"));
        if ((String) session.getAttribute("Username") == null) {
            response.sendRedirect("Home");
        } else {
            String outcome = request.getParameter("outcome");
            PicModel pm = new PicModel();
            pm.setCluster(cluster);
            pm.insertLike(user, picid, outcome);
            if (destination.equals("userpics")) {
                response.sendRedirect(request.getContextPath() + "/Images/" + userName);
            } else {
                response.sendRedirect("Feed");
            }
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
