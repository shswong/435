// Written by Stephen Wong
//
import java.sql.*;
////////////////////////////////////////////////////////////////////////////////
class HW3
{
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      String url = "jdbc:odbc:EPR";

      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      Connection con = DriverManager.getConnection(url, "myLogin", "myPassword");
      
      printTables(con);
      printTableB(con, "SELECT E.EID, ENAME " +
                       "FROM E, EPR " +
                       "WHERE EPR.EID = E.EID and PID='23';");
      
      printTableC(con, "SELECT * " +
                       "FROM E " +
                       "WHERE SALARY>45000;");
      
      printTableD(con, "SELECT P.PID, PNAME " +
                       "FROM P, EPR " +
                       "WHERE RID='18' and P.PID = EPR.PID;");
      
      printTableE(con, "SELECT DISTINCT PNAME " +
                       "FROM EPR, P " +
                       "WHERE EPR.PID = P.PID;");
      
      printTableF(con, "SELECT PNAME FROM EPR,P WHERE EID = 'A132' and P.PID = EPR.PID " +
                       "UNION SELECT PNAME FROM EPR,P WHERE EID = 'D202' and P.PID = EPR.PID;");
      
      printTableG(con, "SELECT R.RID, RNAME " +
                       "FROM R, EPR " +
                       "WHERE EPR.EID = 'C789' and EPR.RID = R.RID;");
      
      printTableH(con, "SELECT E.EID, ENAME, PNAME " +
                       "FROM E, P, EPR " +
                       "WHERE RID='22' and P.PID = EPR.PID and E.EID = EPR.EID;");

      con.close();
   }
//------------------------------------------------------------------------------
   private static void printTables (Connection con) throws Exception
   {
	  Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("select * from E");
      
      System.out.println("EID\t"+"ENAME\t"+"SALARY");
      while (rs.next())
      {
         String eID = rs.getString("EID");
         String eName = rs.getString("ENAME");
         int salary = rs.getInt("SALARY");
         System.out.println(eID+"\t"+eName+"\t"+salary);
      }
      
      rs = stmt.executeQuery("select * from P");
      
      System.out.println("\nPID\t"+"PNAME\t");
      while (rs.next())
      {
    	  String pID = rs.getString("PID");
    	  String pName = rs.getString("PNAME");
    	  System.out.println(pID+"\t"+pName);
      }
      
      rs = stmt.executeQuery("select * from R");
      
      System.out.println("\nRID\t"+"RNAME\t");
      while (rs.next())
      {
    	  String rID = rs.getString("RID");
    	  String rName = rs.getString("RNAME");
    	  System.out.println(rID+"\t"+rName);
      }
      
      rs = stmt.executeQuery("select * from EPR");
      
      System.out.println("\nEID\t"+"PID\t"+"RID");
      while (rs.next())
      {
         String eID = rs.getString("EID");
         String pID = rs.getString("PID");
         String rID = rs.getString("RID");
         System.out.println(eID+"\t"+pID+"\t"+rID);
      }
      System.out.println();
      
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableB (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table B\nENAME\tEID");
      while (rs.next())
      {
         String eID = rs.getString("EID");
         String eName = rs.getString("ENAME");
         System.out.println(eName+"\t"+eID);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableC (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table C\nEID\tENAME\tSALARY");
      while (rs.next())
      {
         String eID = rs.getString("EID");
         String eName = rs.getString("ENAME");
         int salary = rs.getInt("SALARY");
         System.out.println(eID+"\t"+eName+"\t"+salary);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableD (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table D\nPID\tPNAME");
      while (rs.next())
      {
         String pID = rs.getString("PID");
         String pName = rs.getString("PNAME");
         System.out.println(pID+"\t"+pName);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableE (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table E\nPNAME");
      while (rs.next())
      {
         String pName = rs.getString("PNAME");
         System.out.println(pName);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableF (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table F\nPNAME");
      while (rs.next())
      {
         String pName = rs.getString("PNAME");
         System.out.println(pName);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableG (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table G\nRID\tRNAME");
      while (rs.next())
      {
         String rID = rs.getString("RID");
         String rName = rs.getString("RNAME");
         System.out.println(rID+"\t"+rName);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void printTableH (Connection con, String query) throws Exception
   {
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      System.out.println("Table H\nEID\tENAME\tPNAME");
      while (rs.next())
      {
         String eID = rs.getString("EID");
         String eName = rs.getString("ENAME");
         String pName = rs.getString("PNAME");
         System.out.println(eID+"\t"+eName+"\t"+pName);
      }
      System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
      rs.close();
      stmt.close();
   }
} // end class HW3
////////////////////////////////////////////////////////////////////////////////
