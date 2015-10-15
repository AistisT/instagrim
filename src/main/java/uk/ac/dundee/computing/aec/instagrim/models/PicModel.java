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
        Convertors convertor = new Convertors();
        String types[] = Convertors.SplitFiletype(type);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        int length = b.length;
        java.util.UUID picid = convertor.getTimeUUID();
        byte[] thumbb = picresize(picid.toString(), types[1], b);
        int thumblength = thumbb.length;
        ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
        byte[] processedb = picdecolour(picid.toString(), types[1], b);
        ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
        int processedlength = processedb.length;
        Session session = cluster.connect("instagrim");
        PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image,thumb,processed, user, date,imagelength,thumblength,processedlength,type,name) values(?,?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
        BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
        BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);
        Date DateAdded = new Date();
        System.out.println(DateAdded);
        session.execute(bsInsertPic.bind(picid, buffer, thumbbuf, processedbuf, user, DateAdded, length, thumblength, processedlength, type, name));
        session.execute(bsInsertPicToUser.bind(picid, user, DateAdded));
        session.close();
    }

    public void insertProfilePic(byte[] b, String type, String name, String user) {
        Convertors convertor = new Convertors();
        String types[] = Convertors.SplitFiletype(type);
        ByteBuffer buffer = ByteBuffer.wrap(b);
        int length = b.length;
        java.util.UUID picid = convertor.getTimeUUID();
        byte[] thumbb = picresize(picid.toString(), types[1], b);
        int thumblength = thumbb.length;
        ByteBuffer thumbbuf = ByteBuffer.wrap(thumbb);
        byte[] processedb = picdecolour(picid.toString(), types[1], b);
        ByteBuffer processedbuf = ByteBuffer.wrap(processedb);
        int processedlength = processedb.length;
        Session session = cluster.connect("instagrim");
        java.util.UUID currentPicid = getProfilePicId(user);
        System.out.println(user);
        if (currentPicid == null) {
            System.out.println("inside");
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
        session.close();
    }

    public java.util.UUID getProfilePicId(String username) {
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select profilepic from userprofiles where login =?");
        ResultSet rs = null;
        System.out.println(username);
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
        System.out.println(User);
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from ProfilePics where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind(User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned profilespic");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);
                System.out.println("inside");
            }
        }
        session.close();
        return Pics;
    }

    public byte[] picresize(String picid, String type, byte[] b) {
        try {
            InputStream is = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(is);
            BufferedImage thumbnail = createThumbnail(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

    public byte[] picdecolour(String picid, String type, byte[] b) {
        try {
            InputStream is = new ByteArrayInputStream(b);
            BufferedImage BI = ImageIO.read(is);
            BufferedImage processed = createProcessed(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (IOException et) {

        }
        return null;
    }

   public static BufferedImage createThumbnail(BufferedImage img) {
        img = resize(img, Method.QUALITY, 250, OP_ANTIALIAS);
        // Let's add a little border before we return result.
        return img;
    }

   public static BufferedImage createProcessed(BufferedImage img) {
        int Width = img.getWidth();
        img = resize(img, Method.QUALITY, Width, OP_ANTIALIAS);
        return img;
    }

    public LinkedList<Pic> getPicsForUser(String User) {
        LinkedList<Pic> Pics = new LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user =?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        User));
        if (rs.isExhausted()) {
            System.out.println("No Images returned user");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);
            }
        }
        session.close();
        return Pics;
    }

    public LinkedList<Pic> getPics() {
        LinkedList<Pic> Pics = new LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from Pics LIMIT 20");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute(boundStatement.bind());
        if (rs.isExhausted()) {
            System.out.println("No Images returned frontPics");
            return null;
        } else {
            for (Row row : rs) {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);
            }
        }
        session.close();
        return Pics;
    }

    public LinkedList<Pic> getPicsFeed(String username) {
        User user = new User();
        user.setCluster(cluster);
        List<String> followList;
        followList = user.getFollow(username, "following");
        Map<Date, Pic> map = new TreeMap<>();
        Session session = cluster.connect("instagrim");
        for (int i = 0; i < followList.size(); i++) {
            PreparedStatement ps = session.prepare("select picid,date from Pics where user=? LIMIT 20");
            ResultSet rs = null;
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute(boundStatement.bind(followList.get(i)));
            if (rs.isExhausted()) {
                System.out.println("No Images returned feed");
                return null;
            } else {
                for (Row row : rs) {
                    Pic pic = new Pic();
                    java.util.UUID UUID = row.getUUID("picid");
                    Date date = row.getDate("date");
                    System.out.println("UUID" + UUID.toString());
                    pic.setUUID(UUID);
                    map.put(date, pic);
                }
            }
        }
        Map<Date, Pic> reverseMap = new TreeMap(Collections.reverseOrder());
        reverseMap.putAll(map);
        LinkedList<Pic> Pics = new LinkedList<>(reverseMap.values());
        if (Pics.size() >= 20) {
            Pics.subList(20, Pics.size()).clear();
        }
        session.close();
        return Pics;
    }

    public Pic getPic(int image_type, java.util.UUID picid, String tableName) {
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        try {
            ResultSet rs = null;
            PreparedStatement ps = null;
            System.out.println(tableName);
            if (image_type == Convertors.DISPLAY_IMAGE) {
                // needs fixing for profile pic
                ps = session.prepare("select image,imagelength,type from " + tableName + " where picid =?");
            } else if (image_type == Convertors.DISPLAY_THUMB) {
                ps = session.prepare("select thumb,imagelength,thumblength,type from " + tableName + " where picid =?");
            } else if (image_type == Convertors.DISPLAY_PROCESSED) {
                ps = session.prepare("select processed,processedlength,type from " + tableName + " where picid =?");
            }
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted()) {
                System.out.println("No Images returned "+tableName);
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
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;

    }

}
