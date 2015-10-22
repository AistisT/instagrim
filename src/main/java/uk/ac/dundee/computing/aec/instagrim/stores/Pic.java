package uk.ac.dundee.computing.aec.instagrim.stores;

import com.datastax.driver.core.utils.Bytes;
import java.nio.ByteBuffer;
import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Pic implements Comparable<Pic> {

    private ByteBuffer bImage = null;
    private int length;
    private String type;
    private java.util.UUID UUID=null;
    private Date date;
    private String tableName;
    
    
    public void Pic() {

    }
    @Override
    public int compareTo(Pic pic){
        if( date.after(pic.date))
        {
            return 1;
        }
        else if (date.equals(pic.date))
        {
            return 0;
        }
        else 
            return -1;
    }
    
        public void setDate(Date date)
    {
        this.date=date;
    }
       public Date getDate()
    {
        return date;
    }
    
    
    public void setTableName(String tableName)
    {
        this.tableName=tableName;
    }
       public String getTableName()
    {
        return tableName;
    }
    
    public void setUUID(java.util.UUID UUID){
        this.UUID =UUID;
    }
    public String getSUUID(){
        return UUID.toString();
    }
    public void setPic(ByteBuffer bImage, int length,String type) {
        this.bImage = bImage;
        this.length = length;
        this.type=type;
    }

    public ByteBuffer getBuffer() {
        return bImage;
    }

    public int getLength() {
        return length;
    }
    
    public String getType(){
        return type;
    }

    public byte[] getBytes() {
         
        byte image[] = Bytes.getArray(bImage);
        return image;
    }

}
