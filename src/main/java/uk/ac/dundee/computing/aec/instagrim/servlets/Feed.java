package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

public class Feed extends HttpServlet {

    Cluster cluster = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd;
        HttpSession session = request.getSession(true);
        String username = (String) session.getAttribute("Username");
        if (username != null) {
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            java.util.LinkedList<Pic> lsPics = tm.getPicsFeed(username);
            request.setAttribute("Pics", lsPics);
            rd = request.getRequestDispatcher("feed.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("Login");
        }
    }
}
