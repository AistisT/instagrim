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
import java.util.List;
import java.util.ArrayList;
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
        if (!checkIfExists(username))
        {
            Session session = cluster.connect("instagrim");
            PreparedStatement ps = session.prepare("INSERT INTO userprofiles (login,password,first_name,last_name,email) Values(?,?,?,?,?)");
            BoundStatement boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(username, EncodedPassword, firstName, lastName, email));
            Date date = new Date();
            ps = session.prepare("INSERT INTO userlist (user,date) Values(?,?)");
            boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(username, date));
            return true;
        }
        return false;
    }

    public boolean followUser(String username, String userToFollow, String command) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("update userprofiles set following=? where login =?");
        List<String> followList;
        followList = getFollow(username, "following");
        // secondary list, because java decided to be silly and wont allow adding new userToFollow to returned list..
        List<String> followArray = new ArrayList<>();
        followArray.addAll(followList);
        if (command.equals("unfollow")) {
            followArray.remove(userToFollow);
        } else if (command.equals("follow")) {
            followArray.add(userToFollow);
        }
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(followArray, username));
        ps = session.prepare("update userprofiles set followers=? where login =?");
        //Java is being silly again...
        List<String> followList1;
        followList1 = getFollow(userToFollow, "followers");
        followArray.clear();
        followArray.addAll(followList1);
        if (command.equals("unfollow")) {
            followArray.remove(userToFollow);
        } else if (command.equals("follow")) {
            followArray.add(userToFollow);
        }
        boundStatement = new BoundStatement(ps);
        session.execute(boundStatement.bind(followArray, userToFollow));
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

    public List<String> getFollow(String user, String field) {
        List<String> followlist = new LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select " + field + " from userprofiles where login =?");
        ResultSet rs;
        BoundStatement friends = new BoundStatement(ps);
        rs = session.execute(friends.bind(user));
        if (!rs.isExhausted())//If there is a result
        {
            for (Row row : rs) {
                followlist = row.getList(field, String.class);
            }
        }
        return followlist;
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

    public ArrayList getUserinfo(String username) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select first_name,last_name,email from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        ArrayList<String> info = new ArrayList<>();
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        if (rs.isExhausted()) {
            for (int i = 0; i < 3; i++) {
                info.add(null);
            }
        } else {
            for (Row row : rs) {
                info.add(row.getString("first_name"));
                info.add(row.getString("last_name"));
                info.add(row.getString("email"));
            }
        }
        return info;
    }

    public boolean updateDetails(String username, String firstName, String lastName, String email) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("UPDATE userprofiles SET first_name=?, last_name=?, email=? where login=?");
        BoundStatement boundStatement = new BoundStatement(ps);
        System.out.println(firstName+" "+lastName+" "+email+" "+username);
        session.execute(boundStatement.bind(firstName, lastName, email, username));
        return true;
    }
    
        public boolean checkIfExists(String username) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select login from userprofiles where login=?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        String login="";
        System.out.println("user= "+username+ " login="+login);
        rs = session.execute(boundStatement.bind(username));
        if (rs.isExhausted()) {
        } else {
            for (Row row : rs) {
                login= row.getString("login");
            }
        }
        System.out.println("user= "+username+ " login="+login);
        return login.equals(username);
    }
    
    

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }
}
