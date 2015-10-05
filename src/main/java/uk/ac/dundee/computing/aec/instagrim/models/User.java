/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;

/**
 *
 * @author Administrator
 */
public class User {

    Cluster cluster;

    public User() {

    }

    public boolean RegisterUser(String username, String Password, String firstName, String lastName, String email) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;

        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        try {
            Session session = cluster.connect("instagrim");
            PreparedStatement ps = session.prepare("INSERT INTO userprofiles (login,password,first_name,last_name,email) Values(?,?,?,?,?) IF NOT EXISTS");
            BoundStatement boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(username, EncodedPassword, firstName, lastName, email));
            Date date = new Date();
            ps = session.prepare("INSERT INTO userlist (user,date) Values(?,?) IF NOT EXISTS");
            boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(username, date));
        } catch (Exception e) {
            System.out.println(username + " already exists " + e);
        }
        return true;
    }

    public boolean followUser(String username, String userToFollow) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("INSERT INTO userprofiles (following) Values(?) where login =?");
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(username, userToFollow));
        ps = session.prepare("INSERT INTO userprofiles (followers) Values(?) where login =?");
        boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(userToFollow, username));
        return true;
    }

    public boolean checkFollowing(String user, String userToFollow) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select following from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind(user));
        java.util.List<String> followingList = new java.util.LinkedList<>();
        boolean result = false;
        if (rs.isExhausted()) {
            System.out.println("Following no users");
            return false;
        } else {

            for (Row row : rs) {
                followingList = row.getList("following", String.class);
            }
        }
        for (int i = 0; i < followingList.size(); i++) {
            if (followingList.get(i).equals(userToFollow)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public LinkedList getUserList() {
        LinkedList<String> userList = new LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select user from userlist LIMIT 20");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind());
        if (rs.isExhausted()) {
            System.out.println("No registered users");
            return null;
        } else {
            for (Row row : rs) {
                String user = row.getString("user");
                userList.add(user);
            }
        }
        return userList;
    }

    public boolean IsValidUser(String username, String Password) {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String EncodedPassword = null;
        try {
            EncodedPassword = sha1handler.SHA1(Password);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException et) {
            System.out.println("Can't check your password");
            return false;
        }
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            System.out.println("No Images returned");
            return false;
        } else {
            for (Row row : rs) {

                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
