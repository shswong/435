// Written by Stephen Wong
//
import java.sql.*;
////////////////////////////////////////////////////////////////////////////////
class HW2
{
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      String url = "jdbc:odbc:EPR";
      String query1 = "select * from E";
      String query2 = "select * from P";
      String query3 = "select * from R";
      String query4 = "select * from EPR";

      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      Connection con = DriverManager.getConnection(url, "myLogin", "myPassword");
      Statement stmt1 = con.createStatement();
      Statement stmt2 = con.createStatement();
      Statement stmt3 = con.createStatement();
      Statement stmt4 = con.createStatement();
      ResultSet rs1 = stmt1.executeQuery(query1);
      ResultSet rs2 = stmt2.executeQuery(query2);
      ResultSet rs3 = stmt3.executeQuery(query3);
      ResultSet rs4 = stmt4.executeQuery(query4);

      printTable(rs1, rs2, rs3, rs4);

      stmt1.executeUpdate("INSERT INTO E VALUES ('X555', 'Wong', 44444)");
      stmt4.executeUpdate("INSERT INTO EPR VALUES('X555', '23', '22')");
      stmt4.executeUpdate("INSERT INTO EPR VALUES('X555', '14', '41')");
      
      stmt1 = con.createStatement();
      stmt2 = con.createStatement();
      stmt3 = con.createStatement();
      stmt4 = con.createStatement();
      rs1 = stmt1.executeQuery(query1);
      rs2 = stmt2.executeQuery(query2);
      rs3 = stmt3.executeQuery(query3);
      rs4 = stmt4.executeQuery(query4);
      
      printTable(rs1, rs2, rs3, rs4);
      
      rs4.close();
      rs3.close();
      rs2.close();
      rs1.close();
      stmt4.close();
      stmt3.close();
      stmt2.close();
      stmt1.close();
      con.close();
   }
//------------------------------------------------------------------------------
   private static void printTable ( ResultSet rs1, ResultSet rs2, ResultSet rs3, ResultSet rs4 ) throws Exception
   {
      System.out.println("EID\t"+"ENAME\t"+"SALARY");
      while (rs1.next())
      {
         String eID = rs1.getString("EID");
         String eName = rs1.getString("ENAME");
         int salary = rs1.getInt("SALARY");
         System.out.println(eID+"\t"+eName+"\t"+salary);
      }
      System.out.println("\nPID\t"+"PNAME\t");
      while (rs2.next())
      {
    	  String pID = rs2.getString("PID");
    	  String pName = rs2.getString("PNAME");
    	  System.out.println(pID+"\t"+pName);
      }
      System.out.println("\nRID\t"+"RNAME\t");
      while (rs3.next())
      {
    	  String rID = rs3.getString("RID");
    	  String rName = rs3.getString("RNAME");
    	  System.out.println(rID+"\t"+rName);
      }
      System.out.println("\nEID\t"+"PID\t"+"RID");
      while (rs4.next())
      {
         String eID = rs4.getString("EID");
         String pID = rs4.getString("PID");
         String rID = rs4.getString("RID");
         System.out.println(eID+"\t"+pID+"\t"+rID);
      }
      System.out.println();
   }
//------------------------------------------------------------------------------
} // end class TestJDBC
////////////////////////////////////////////////////////////////////////////////
