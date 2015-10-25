package uk.ac.dundee.computing.aec.instagrim.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Pic implements Comparable<Pic> {

    private ByteBuffer bImage = null;
    private int length;
    private java.util.UUID UUID = null;
    private Date date;
    private String tableName,owner,type;
    int likes, dislikes;
    boolean liked, disliked;

    public void Pic() {

    }

    @Override
    public int compareTo(Pic pic) {
        if (date.after(pic.date)) {
            return 1;
        } else if (date.equals(pic.date)) {
            return 0;
        } else {
            return -1;
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
        public String getDateFormated() {
            DateFormat dateFormat=new SimpleDateFormat("HH:mm:ss dd.MM.yyy", Locale.UK);
        return  dateFormat.format(date);
    }

    public void setDisliked(boolean disliked) {
        this.disliked = disliked;
    }

    public boolean getDisliked() {
        return disliked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public boolean getLiked() {
        return liked;
    }

    public void setDislikes(int dislike) {
        this.dislikes = dislike;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
    
     public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }
    

    public void setUUID(java.util.UUID UUID) {
        this.UUID = UUID;
    }
      public java.util.UUID getUUID() {
        return UUID;
    }

    public String getSUUID() {
        return UUID.toString();
    }

    public void setPic(ByteBuffer bImage, int length, String type) {
        this.bImage = bImage;
        this.length = length;
        this.type = type;
    }

    public ByteBuffer getBuffer() {
        return bImage;
    }

    public int getLength() {
        return length;
    }

    public String getType() {
        return type;
    }

    public byte[] getBytes() {

        byte image[] = Bytes.getArray(bImage);
        return image;
    }

}