package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;
import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

public class PicModel {

    Cluster cluster;

    public void PicModel() {
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public void insertPic(byte[] b, String type, String name, String user) {
        String types[] = Convertors.SplitFiletype(type);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        int length = b.length;
        java.util.UUID picid = Convertors.getTimeUUID();
        byte[] thumbb = picresize(types[1], b);
        int thumblength = thumbb.length;
        ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
        byte[] processedb = picdecolour(types[1], b);
        ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
        int processedlength = processedb.length;
        try (Session session = cluster.connect("instagrinAistis")) {
            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, date,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
            PreparedStatement psInsertLikesList = session.prepare("insert into likeslist ( picid) values(?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);
            BoundStatement bsInsertLikesList = new BoundStatement(psInsertLikesList);
            Date DateAdded = new Date();
            session.execute(bsInsertPic.bind(picid, buffer, thumbbuf, processedbuf, user, DateAdded, length, thumblength, processedlength, type, name));
            session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
            session.execute(bsInsertLikesList.bind(picid));
        }
    }

    public void insertProfilePic(byte[] b, String type, String name, String user) {
        String types[] = Convertors.SplitFiletype(type);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        int length = b.length;
        java.util.UUID picid = Convertors.getTimeUUID();
        byte[] thumbb = picresize(types[1], b);
        int thumblength = thumbb.length;
        ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
        byte[] processedb = picdecolour(types[1], b);
        ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
        int processedlength = processedb.length;
        try (Session session = cluster.connect("instagrinAistis")) {
            java.util.UUID currentPicid = getProfilePicId(user);
            if (currentPicid == null) {
                PreparedStatement psInsertPic = session.prepare("insert into ProfilePics ( picid, image,thumb,processed, user,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?)");
                BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
                session.execute(bsInsertPic.bind(picid, buffer, thumbbuf, processedbuf, user, length, thumblength, processedlength, type, name));

                PreparedStatement psInsertProfilePic = session.prepare("UPDATE userprofiles SET profilepic=? where login=?");
                BoundStatement bsInsertProfilePic = new BoundStatement(psInsertProfilePic);
                session.execute(bsInsertProfilePic.bind(picid, user));
            } else {
                PreparedStatement psInsertPic = session.prepare("UPDATE ProfilePics SET image=?, thumb=?, processed=?,imagelength=?,thumblength=?,processedlength=?,type=?,name=? where picid=?");
                BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
                session.execute(bsInsertPic.bind(buffer, thumbbuf, processedbuf, length, thumblength, processedlength, type, name, currentPicid));
            }
        }
    }

    public java.util.UUID getProfilePicId(String username) {
        Session session = cluster.connect("instagrinAistis");
        PreparedStatement ps = session.prepare("select profilepic from userprofiles where login =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        java.util.UUID picid = null;
        rs = session.execute(boundStatement.bind(username));
        if (rs.isExhausted()) {
        } else {
            for (Row row : rs) {
                picid = row.getUUID("profilepic");
            }
        }
        return picid;
    }

    public LinkedList<Pic> getProfilePic(String User) {
        LinkedList<Pic> Pics = new LinkedList<>();
        try (Session session = cluster.connect("instagrinAistis")) {
            PreparedStatement ps = session.prepare("select picid from ProfilePics where user =?");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(User));
            if (rs.isExhausted()) {
                return null;
            } else {
                for (Row row : rs) {
                    Pic pic = new Pic();
                    java.util.UUID UUID = row.getUUID("picid");
                    pic.setUUID(UUID);
                    Pics.add(pic);
                }
            }
        }
        return Pics;
    }

    private byte[] picresize(String type, byte[] b) {
        try {
            InputStream is = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(is);
            BufferedImage thumbnail = createThumbnail(BI);
            byte[] imageInByte;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(thumbnail, type, baos);
                baos.flush();
                imageInByte = baos.toByteArray();
            }
            return imageInByte;
        } catch (IOException et) {
            System.out.println("Error= " + et);
        }
        return null;
    }

    private byte[] picdecolour(String type, byte[] b) {
        try {
            InputStream is = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(is);
            BufferedImage processed = createProcessed(BI);
            byte[] imageInByte;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                ImageIO.write(processed, type, baos);
                baos.flush();
                imageInByte = baos.toByteArray();
            }
            return imageInByte;
        } catch (IOException et) {
            System.out.println("Error= " + et);
        }
        return null;
    }

    private static BufferedImage createThumbnail(BufferedImage img) {
        img = resize(img, Method.QUALITY, 250, OP_ANTIALIAS);
        return img;
    }

    private static BufferedImage createProcessed(BufferedImage img) {
        int Width = img.getWidth();
        img = resize(img, Method.QUALITY, Width, OP_ANTIALIAS);
        return img;
    }

    public LinkedList<Pic> getPicsForUser(String User,String userName) {
        LinkedList<Pic> Pics = new LinkedList<>();
        try (Session session = cluster.connect("instagrinAistis")) {
            PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(User));
            if (rs.isExhausted()) {
                return null;
            } else {
                for (Row row : rs) {
                    Pic pic = new Pic();
                    java.util.UUID UUID = row.getUUID("picid");
                    pic.setUUID(UUID);
                    setLikes(pic, userName);
                    Pics.add(pic);
                }
            }
        }
        return Pics;
    }

    private void setLikes(Pic pic, String user) {
        LinkedList<String> userlistLike = getLikes(pic.getUUID(), "like");
        LinkedList<String> userlistDislike = getLikes(pic.getUUID(), "dislike");
        pic.setLikes(userlistLike.size());
        pic.setLiked(userlistLike.contains(user));
        pic.setDislikes(userlistDislike.size());
        pic.setDisliked(userlistDislike.contains(user));
    }

    public LinkedList<Pic> getPics() {
        LinkedList<Pic> Pics = new LinkedList<>();
        try (Session session = cluster.connect("instagrinAistis")) {
            PreparedStatement ps = session.prepare("select picid,date,user from Pics LIMIT 20");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind());
            if (rs.isExhausted()) {
                return null;
            } else {
                for (Row row : rs) {
                    Pic pic = new Pic();
                    java.util.UUID UUID = row.getUUID("picid");
                    Date date = row.getDate("date");
                    String user =row.getString("user");
                    pic.setUUID(UUID);
                    pic.setDate(date);
                    pic.setOwner(user);
                    Pics.add(pic);
                }
            }
            Collections.sort(Pics, Collections.reverseOrder());
        }
        return Pics;
    }

    public void deletePicture(String picid) {
        try (Session session = cluster.connect("instagrinAistis")) {
            PreparedStatement ps = session.prepare("delete from Pics where picid=?");
            BoundStatement boundStatement = new BoundStatement(ps);
            session.execute(boundStatement.bind(java.util.UUID.fromString(picid)));
            
            PreparedStatement ps1 = session.prepare("delete from likeslist where picid=?");
            BoundStatement boundStatement1 = new BoundStatement(ps1);
            session.execute(boundStatement1.bind(java.util.UUID.fromString(picid)));
            
            ResultSet rs = null;
            PreparedStatement ul = session.prepare("select * from userpiclist where picid=?");
            BoundStatement boundStatementUl = new BoundStatement(ul);
            rs = session.execute(boundStatementUl.bind(java.util.UUID.fromString(picid)));
            String user = "";
            Date date = new Date();
            if (rs.isExhausted()) {
            } else {
                for (Row row : rs) {
                    user = row.getString("user");
                    date = row.getDate("pic_added");
                }
            }
            PreparedStatement usl = session.prepare("delete from userpiclist where user=? and pic_added=?");
            BoundStatement boundStatementUsl = new BoundStatement(usl);
            session.execute(boundStatementUsl.bind(user, date));
        }
    }

    public LinkedList<Pic> getPicsFeed(String username) {
        User user = new User();
        user.setCluster(cluster);
        List<String> followList;
        followList = user.getFollow(username, "following");
        Map<Date, Pic> map = new TreeMap<>();
        LinkedList<Pic> Pics;
        try (Session session = cluster.connect("instagrinAistis")) {
            for (String followList1 : followList) {
                PreparedStatement ps = session.prepare("select picid,date from Pics where user=? LIMIT 20");
                ResultSet rs = null;
                BoundStatement boundStatement = new BoundStatement(ps);
                rs = session.execute(boundStatement.bind(followList1));
                if (rs.isExhausted()) {
                } else {
                    for (Row row : rs) {
                        Pic pic = new Pic();
                        java.util.UUID UUID = row.getUUID("picid");
                        Date date = row.getDate("date");
                        pic.setDate(date);
                        pic.setUUID(UUID);
                        pic.setOwner(followList1);
                        setLikes(pic, username);
                        map.put(date, pic);
                    }
                }
            }
            Map<Date, Pic> reverseMap = new TreeMap(Collections.reverseOrder());
            reverseMap.putAll(map);
            Pics = new LinkedList<>(reverseMap.values());
            if (Pics.size() >= 20) {
                Pics.subList(20, Pics.size()).clear();
            }
        }
        return Pics;
    }

    public Pic getPic(int image_type, java.util.UUID picid, String tableName) {
        ByteBuffer bImage;
        String type;
        int length;
        try (Session session = cluster.connect("instagrinAistis")) {
            bImage = null;
            type = null;
            length = 0;
            try {
                ResultSet rs = null;
                PreparedStatement ps = null;
                if (image_type == Convertors.DISPLAY_IMAGE) {
                    ps = session.prepare("select image,imagelength,type from " + tableName + " where picid =?");
                } else if (image_type == Convertors.DISPLAY_THUMB) {
                    ps = session.prepare("select thumb,imagelength,thumblength,type from " + tableName + " where picid =?");
                } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                    ps = session.prepare("select processed,processedlength,type from " + tableName + " where picid =?");
                }
                BoundStatement boundStatement = new BoundStatement(ps);
                rs = session.execute(boundStatement.bind(picid));
                if (rs.isExhausted()) {
                    return null;
                } else {
                    for (Row row : rs) {
                        if (image_type == Convertors.DISPLAY_IMAGE) {
                            bImage = row.getBytes("image");
                            length = row.getInt("imagelength");
                        } else if (image_type == Convertors.DISPLAY_THUMB) {
                            bImage = row.getBytes("thumb");
                            length = row.getInt("thumblength");

                        } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                            bImage = row.getBytes("processed");
                            length = row.getInt("processedlength");
                        }
                        type = row.getString("type");
                    }
                }
            } catch (Exception et) {
                System.out.println("Can't get Pic" + et);
                return null;
            }
        }
        Pic p = new Pic();
        p.setPic(bImage, length, type);
        return p;
    }

    public void insertLike(String user, java.util.UUID picid, String field) {

        try (Session session = cluster.connect("instagrinAistis")) {
            LinkedList<String> likes = getLikes(picid, field);
            LinkedList<String> dislikes = getLikes(picid, field);
            switch (field) {
                case "like":
                    likes.add(user);
                    dislikes.clear();
                    dislikes.addAll(removeLikes(picid, "dislike", user));
                    break;
                case "dislike":
                    dislikes.add(user);
                    likes.clear();
                    likes.addAll(removeLikes(picid, "like", user));
                    break;
            }
            PreparedStatement psInsertLikesList = session.prepare("update likeslist set like=?, dislike=? where picid=?");
            BoundStatement bsLikesList = new BoundStatement(psInsertLikesList);
            session.execute(bsLikesList.bind(likes, dislikes, picid));
        }

    }

    private LinkedList<String> removeLikes(java.util.UUID picid, String field, String user) {
        LinkedList<String> likes = getLikes(picid, field);
        if (likes.contains(user)) {
            likes.remove(user);
        }
        return likes;
    }

    private LinkedList<String> getLikes(java.util.UUID picid, String field) {
        LinkedList<String> likes = new LinkedList<>();
        Session session = cluster.connect("instagrinAistis");
        PreparedStatement ps = session.prepare("select " + field + " from likeslist where picid =?");
        ResultSet rs;
        BoundStatement friends = new BoundStatement(ps);
        rs = session.execute(friends.bind(picid));
        if (!rs.isExhausted()) {
            for (Row row : rs) {
                likes.addAll(row.getList(field, String.class));
            }
        }
        return likes;
    }
}