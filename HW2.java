// Written by Stephen Wong
//
import java.sql.*;
////////////////////////////////////////////////////////////////////////////////
class HW2
{
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      String url = "jdbc:odbc:suppliers & parts";
      String query = "select * from S";

      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      Connection con = DriverManager.getConnection(url, "myLogin", "myPassword");
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
		
		printTable(rs);
																	
		//stmt.executeUpdate("INSERT INTO S VALUES ('s8', 'asda', 23, 'asdf')");

		rs = stmt.executeQuery(query);
		
      rs.close();
		stmt.close();
		con.close();
   }
//------------------------------------------------------------------------------
   private static void printTable ( ResultSet rs ) throws Exception
	{
	   while (rs.next())
		{
		   String sID = rs.getString("SID");
	 	   String sName = rs.getString("SNAME");
         int status = rs.getInt("STATUS");
		   String city = rs.getString("CITY");
         System.out.println(sID+"\t"+sName+"\t"+status+"\t"+city);
		}
	}
//------------------------------------------------------------------------------
} // end class TestJDBC
////////////////////////////////////////////////////////////////////////////////
