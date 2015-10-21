package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;



public class Default extends HttpServlet {
    

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

        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        LinkedList<Pic> lsPics = tm.getPics();
        request.setAttribute("Pics", lsPics);

        User user = new User();
        user.setCluster(cluster);
        LinkedList<String> userList = user.getUserList();
        request.setAttribute("userList", userList);
        LinkedList<LinkedList> profilePics = new LinkedList<>();
        if (userList != null) {
            for (String userList1 : userList) {
                profilePics.add(tm.getProfilePic(userList1));
            }
        }
        request.setAttribute("ProfilePicsList", profilePics);

        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
