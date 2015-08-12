package mario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database{
 static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
 static final String DB_URL="jdbc:mysql://localhost/mario";
 static final String USER="root";
 static final String PASS="";
     Main m;
     Connection conn=null; 
     Statement stmt=null;
     ResultSet rs=null;

 Database(Main m){
     this.m=m;
try{
    Class.forName(JDBC_DRIVER);
    conn=DriverManager.getConnection(DB_URL,USER,PASS);
    stmt=conn.createStatement();
//    String sql="CREATE TABLE USERS(NAME VARCHAR(255),PREVSCORE INTEGER,HIGHESTSCORE INTEGER)";
//    stmt.execute(sql);

}  catch(Exception e){}


 }
 public void registerName(String name) throws SQLException{
String query="SELECT NAME FROM PLAYERS WHERE NAME LIKE '%"+name+"%'";
rs=stmt.executeQuery(query);
int constant=0;
while(rs.next()){
    constant++;
}
if(constant==0){
     String sql="INSERT INTO PLAYERS VALUES "+
             "('"+name+"',"+0+","+0+")";
     stmt.executeUpdate(sql);
     }
 else
    return;
 }
 public String getHighScore() throws SQLException{
     String sql="SELECT MAX(YOURHIGH) AS HIGH FROM PLAYERS";
     rs=stmt.executeQuery(sql);
     rs.next();
     return rs.getString("HIGH");
 }
 public void storeYourHigh(int score,String name) throws SQLException{
   String query="SELECT YOURHIGH FROM PLAYERS WHERE NAME LIKE '%"+name+"%'";
    rs=stmt.executeQuery(query);
    rs.next();
    if(rs.getInt("YOURHIGH")<score){
   String sql="UPDATE PLAYERS SET YOURHIGH="+score+" WHERE NAME = '"+name+"'";
    stmt.execute(sql);
 }  
 }
 public String getyourHighScore(String name) throws SQLException{
  String query="SELECT YOURHIGH FROM PLAYERS WHERE NAME LIKE '%"+name+"%'";
  rs=stmt.executeQuery(query);
  rs.next();
  return rs.getString("YOURHIGH");
 }
 public Object[] getAllPlayersName() throws SQLException{
     String sql="SELECT NAME FROM PLAYERS";
     rs=stmt.executeQuery(sql);
     //int rows=rs.getFetchSize();  //wont't work
     //Moves the cursor to the last row.getFetchSize() will not work here,
      //because the getFetchSize() method only returns the # of rows fetched by the cursor
     //And cursor can fetch only one row at a time.
     rs.last();
     int size=rs.getRow();
     Object[] nameArray=new Object[size];
     int i=0;
     rs.beforeFirst();
     while(rs.next()){
         nameArray[i]=rs.getString("NAME");
         i++;
     }
     rs.close();
     return nameArray;
     
 }
}