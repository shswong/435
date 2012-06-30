// Written by Dr Barry Soroka
//
// Last modified 110119
//
// A first demonstration of using Java to query an Access 2000 database.
//
// We will print the STATUS column of the S table in suppliers & parts.
//
import java.sql.*;
////////////////////////////////////////////////////////////////////////////////
class TestJDBC
{
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      String url = "jdbc:odbc:suppliers & parts";
      String query = "select * from S";

      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      Connection con = DriverManager.getConnection(url,
                                                   "myLogin", 
                                                   "myPassword");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next())
      {
         int status = rs.getInt("STATUS");
         System.out.println(status);
      }
      rs.close();
      stmt.close();
      con.close();
   }
//------------------------------------------------------------------------------
} // end class TestJDBC
////////////////////////////////////////////////////////////////////////////////
