package uk.ac.dundee.computing.aec.instagrim.lib;

import com.datastax.driver.core.*;

public final class Keyspaces {

    public Keyspaces() {

    }

    public static void SetUpKeySpaces(Cluster c) {
        try {
            String createkeyspace = "create keyspace if not exists instagrinAistis  WITH replication = {'class':'SimpleStrategy', 'replication_factor':1}";
            String CreatePicTable = "CREATE TABLE if not exists instagrinAistis.Pics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " date timestamp,"
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + " processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid,date)"
                    + ") WITH CLUSTERING ORDER BY (date DESC);";
            String CreatePicProfilePic = "CREATE TABLE if not exists instagrinAistis.ProfilePics ("
                    + " user varchar,"
                    + " picid uuid, "
                    + " title varchar,"
                    + " image blob,"
                    + " thumb blob,"
                    + " processed blob,"
                    + " imagelength int,"
                    + " thumblength int,"
                    + " processedlength int,"
                    + " type  varchar,"
                    + " name  varchar,"
                    + " PRIMARY KEY (picid)"
                    + ");";

            String CreateIndexOnPicTable = "Create INDEX userfeed ON instagrinAistis.Pics (user)";
            String CreateIndexOnProfilePicTable = "Create INDEX userProfile ON instagrinAistis.ProfilePics (user)";
            String CreateIndexOnUserPicList = "Create INDEX userlistPicid ON instagrinAistis.userpiclist (picid)";

            String Createuserpiclist = "CREATE TABLE if not exists instagrinAistis.userpiclist (\n"
                    + "picid uuid,\n"
                    + "user varchar,\n"
                    + "pic_added timestamp,\n"
                    + "PRIMARY KEY (user,pic_added)\n"
                    + ") WITH CLUSTERING ORDER BY (pic_added desc);";

            String Createlikeslist = "CREATE TABLE if not exists instagrinAistis.likeslist (\n"
                    + "picid uuid,\n"
                    + "like list<text>,\n"
                    + "dislike list<text>,\n"
                    + "PRIMARY KEY (picid)\n"
                    + ");";

            String CreateUserProfile = "CREATE TABLE if not exists instagrinAistis.userprofiles (\n"
                    + "      login text PRIMARY KEY,\n"
                    + "      password text,\n"
                    + "      first_name text,\n"
                    + "      last_name text,\n"
                    + "      following list<text>,\n"
                    + "      followers list<text>,\n"
                    + "      profilepic uuid,\n"
                    + "      email text,\n"
                    + "  );";
            String CreateUserList = "CREATE TABLE if not exists instagrinAistis.userlist ("
                    + " user varchar,"
                    + " date timestamp,"
                    + " PRIMARY KEY (user, date)"
                    + ") WITH CLUSTERING ORDER BY (date DESC);";
            try (Session session = c.connect()) {
                try {
                    PreparedStatement statement = session
                            .prepare(createkeyspace);
                    BoundStatement boundStatement = new BoundStatement(
                            statement);
                    ResultSet rs = session
                            .execute(boundStatement);
                    System.out.println("created instagrim ");
                } catch (Exception et) {
                    System.out.println("Can't create instagrim " + et);
                }

                System.out.println("" + CreateUserList);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateUserList);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create userlist table " + et);
                }
                
                 System.out.println("" + Createlikeslist);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(Createlikeslist);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create likes table " + et);
                }
                
                System.out.println("" + CreateIndexOnUserPicList);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateIndexOnUserPicList);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create userlistpic index " + et);
                }

                System.out.println("" + CreatePicProfilePic);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreatePicProfilePic);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create profilePic table " + et);
                }
                System.out.println("" + CreateIndexOnPicTable);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateIndexOnPicTable);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create indexonpic index " + et);
                }
                System.out.println("" + CreateIndexOnProfilePicTable);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateIndexOnProfilePicTable);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create profilePic index " + et);
                }
                System.out.println("" + CreatePicTable);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreatePicTable);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create pic table " + et);
                }
                System.out.println("" + Createuserpiclist);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(Createuserpiclist);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create user pic list table " + et);
                }
                System.out.println("" + CreateUserProfile);
                try {
                    SimpleStatement cqlQuery = new SimpleStatement(CreateUserProfile);
                    session.execute(cqlQuery);
                } catch (Exception et) {
                    System.out.println("Can't create user Profile " + et);
                }
            }
        } catch (Exception et) {
            System.out.println("Other keyspace or coulm definition error" + et);
        }
    }
}