package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;

public class Followers extends HttpServlet {

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
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("Username");
        if (username != null) {
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            User user = new User();
            user.setCluster(cluster);
            //following
            List<String> followingList = user.getFollow(username, "following");
            LinkedList<String> following = new LinkedList<>();
            following.addAll(followingList);
            request.setAttribute("followingList", following);
            LinkedList<LinkedList> profilePics = new LinkedList<>();
            for (String following1 : following) {
                profilePics.add(tm.getProfilePic(following1));
            }
            request.setAttribute("ProfilePicsList", profilePics);
            //followers
            List<String> followersList = user.getFollow(username, "followers");
            LinkedList<String> followers = new LinkedList<>();
            followers.addAll(followersList);
            request.setAttribute("followersList", followers);
            LinkedList<LinkedList> followersProfilePics = new LinkedList<>();
            for (String followers1 : followers) {
                followersProfilePics.add(tm.getProfilePic(followers1));
            }
            request.setAttribute("followersProfilePics", followersProfilePics);

            RequestDispatcher rd = request.getRequestDispatcher("followers.jsp");
            rd.forward(request, response);
        } else {
            response.sendRedirect("Login");
        }
    }
}