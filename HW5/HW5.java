// Written by Stephen Wong
//
import java.sql.*;
import java.io.*;
import java.util.*;
////////////////////////////////////////////////////////////////////////////////
class HW5
{
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
	   File f = new File("hw05test.txt");
		Scanner s = new Scanner(f);
		
		String url = "jdbc:odbc:ES";
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      Connection con = DriverManager.getConnection(url,
                                                   "myLogin", 
                                                   "myPassword");
      con.setAutoCommit(false);
		
		while (s.hasNext())
		{
		   String line = s.nextLine();
			System.out.println(line);
			
			if (line.equals("check"))
			{
			   check(con);
			}
			else if (line.equals("print"))
			{
			   printTable(con);
			}
			else if (line.contains("insert") || line.contains("update") || line.contains("delete"))
			{
			   command(con, line);
			}
		}
	
      con.close();
   }
//------------------------------------------------------------------------------
   private static void printTable (Connection con) throws Exception
	{
      Statement s = con.createStatement();
		ResultSet rs = s.executeQuery("SELECT * FROM ES");
		
		System.out.println("eid\t\tsalary");
		while (rs.next())
		{
		   int eid = rs.getInt("eid");
			int salary = rs.getInt("salary");
			System.out.println(eid + "\t\t" + salary);
		}
		
		rs.close();
		s.close();
	}
//------------------------------------------------------------------------------
   private static void check (Connection con) throws Exception
   {
      int checkSum = 0;
      Statement s = con.createStatement();
      ResultSet rs = s.executeQuery("SELECT * FROM ES");
      
      while (rs.next())
      {
         checkSum += rs.getInt("salary");
      }
      
      if (checkSum >= 100)
      {
         con.rollback();
         System.out.println("*** rollback performed ***");
      }
      else
      {
         con.commit();
         System.out.println("*** commit performed ***");
      }
      
      rs.close();
      s.close();
   }
//------------------------------------------------------------------------------
   private static void command (Connection con, String query) throws Exception
   {
      Statement s = con.createStatement();
      
      try
      {
         s.executeUpdate(query);
      }
      catch (SQLException e)
      {
         System.out.println("*** SQL error encountered ***");
      }

      s.close();
   }
//------------------------------------------------------------------------------
} // end class HW5
////////////////////////////////////////////////////////////////////////////////
