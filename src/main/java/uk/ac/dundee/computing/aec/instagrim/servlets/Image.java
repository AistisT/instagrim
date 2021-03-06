package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.lib.Convertors;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

@WebServlet(name = "Image", urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/ProfilePic/",
    "/PThumb/",
    "/Images/*"
})
@MultipartConfig

public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();

    public Image() {
        super();
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);
        CommandsMap.put("ProfilePic", 4);
        CommandsMap.put("PThumb", 5);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String args[] = Convertors.SplitRequestPath(request);
        int command;
        try {
            command = (Integer) CommandsMap.get(args[1]);
        } catch (Exception et) {
            response.sendError(404);
            return;
        }
        switch (command) {
            case 1:
                DisplayImage(Convertors.DISPLAY_PROCESSED, args[2], response, "Pics");
                break;
            case 2:
                DisplayImageList(args[2], request, response);
                break;
            case 3:
                DisplayImage(Convertors.DISPLAY_THUMB, args[2], response, "Pics");
                break;
            case 4:
                DisplayImage(Convertors.DISPLAY_PROCESSED, args[2], response, "ProfilePics");
                break;
            case 5:
                DisplayImage(Convertors.DISPLAY_THUMB, args[2], response, "ProfilePics");
                break;
            default:
                response.sendError(404);
        }
    }

    private void DisplayImageList(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = "";
        if (session.getAttribute("Username") != null) {
            userName = (String) session.getAttribute("Username");
        }
        User user = new User();
        user.setCluster(cluster);
        if (user.checkIfExists(User)) {
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            java.util.LinkedList<Pic> pfPics = tm.getProfilePic(User);
            request.setAttribute("ProfilePics", pfPics);
            java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User, userName);
            request.setAttribute("Pics", lsPics);

            ArrayList<String> userDetails = user.getUserinfo(User);
            request.setAttribute("firstName", userDetails.get(0));
            request.setAttribute("lastName", userDetails.get(1));
            request.setAttribute("email", userDetails.get(2));
            request.setAttribute("user", User);

            RequestDispatcher rd = request.getRequestDispatcher("/UsersPics.jsp");
            checkFollowing(request);
            rd.forward(request, response);
        } else {
            response.sendError(404);
        }
    }

    private void DisplayImage(int type, String Image, HttpServletResponse response, String tableName) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        Pic p = tm.getPic(type, java.util.UUID.fromString(Image), tableName);
        if (p == null) {
            response.sendError(404);
        } else {
            try (OutputStream out = response.getOutputStream()) {
                response.setContentType(p.getType());
                response.setContentLength(p.getLength());
                InputStream is = new ByteArrayInputStream(p.getBytes());
                BufferedInputStream input = new BufferedInputStream(is);
                byte[] buffer = new byte[8192];
                for (int length = 0; (length = input.read(buffer)) > 0;) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        for (Part part : request.getParts()) {
            String type = part.getContentType();
            String filename = part.getSubmittedFileName();
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            HttpSession session = request.getSession();
            String uri = (String) session.getAttribute("origin");
            String username = (String) session.getAttribute("Username");
            if (type.equalsIgnoreCase("image/jpeg") || type.equalsIgnoreCase("image/png") || type.equalsIgnoreCase("image/jpg")||type.equalsIgnoreCase("image/bmp")) {
                if (i > 0) {
                    byte[] b = new byte[i + 1];
                    is.read(b);
                    PicModel tm = new PicModel();
                    tm.setCluster(cluster);
                    if (uri.equalsIgnoreCase("Settings")) {
                        tm.insertProfilePic(b, type, filename, username);
                    } else {
                        tm.insertPic(b, type, filename, username);
                    }
                    session.setAttribute("typeFail", false);
                    is.close();
                }
            } else {
                session.setAttribute("typeFail", true);
            }
            if (uri.equalsIgnoreCase("Settings")) {
                response.sendRedirect("Settings");
            } else {
                checkFollowing(request);
                response.sendRedirect("Home");
            }
       }
    }

    protected void checkFollowing(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("Username");
        String args[] = Convertors.SplitRequestPath(request);
        if (username != null) {
            if (args.length == 3) {
                User user = new User();
                user.setCluster(cluster);
                boolean following = user.checkFollowing(username, args[2]);
                session.setAttribute("Following", following);
                session.setAttribute(("userToFollow"), args[2]);
            }
        }
    }
}
