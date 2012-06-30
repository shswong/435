// Written by Stephen Wong
//
import java.sql.*;
import java.util.*;
////////////////////////////////////////////////////////////////////////////////
class HW4
{
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      String url = "jdbc:odbc:EPR";

      Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

      Connection con = DriverManager.getConnection(url,
                                                   "myLogin", 
                                                   "myPassword");
      
      ArrayList<Integer> pid = new ArrayList<Integer>();
      ArrayList<String> pname = new ArrayList<String>();
      
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM P");

      while (rs.next())
      {
         pid.add(rs.getInt("pid"));
         pname.add(rs.getString("pname"));
      }
      
      printTables(con);
      constraintOne(con, pid, pname);
      constraintTwo(con, pid, pname);
      constraintThree(con, pid, pname);
      
      rs.close();
      stmt.close();
      con.close();
   }
//------------------------------------------------------------------------------
   private static void constraintOne (Connection con, ArrayList<Integer> pid, ArrayList<String> pname) throws Exception
   {
      Statement stmt = con.createStatement();
      for (int i = 0; i < pid.size(); i++)
      {
         int managerCounter = 0;
         String query = "SELECT pname, p.pid, count(rid) AS [count] " +
                        "FROM EPR, P " +
                        "WHERE p.pid=" + pid.get(i) + " and rid=22 and p.pid=epr.pid " +
                        "GROUP BY pname, p.pid HAVING count(rid)>=1;";
         
         ResultSet rs = stmt.executeQuery(query);
         if (rs.next()) { managerCounter = rs.getInt("count"); }
         
         if (managerCounter > 1)
         {
            System.out.println("***** Constraint 1 Violation:");
            System.out.println("Project " + pid.get(i) + " (" + pname.get(i) + ") has " + managerCounter + " managers.\n");
         }
         
         else if (managerCounter == 0)
         {
            System.out.println("***** Constraint 1 Violation:");
            System.out.println("Project " + pid.get(i)+ " (" + pname.get(i) + ") has no manager.\n");
         }
         rs.close();
      }
      stmt.close();
   }
//------------------------------------------------------------------------------
   private static void constraintTwo (Connection con, ArrayList<Integer> pid, ArrayList<String> pname) throws Exception
   {
      Statement stmt1 = con.createStatement();
      Statement stmt2 = con.createStatement();
      for (int i = 0; i < pid.size(); i++)
      {
         int programmerCount = 0;
         int assemblerCount = 0;
         String query1 = "SELECT pname, p.pid, rname, r.rid, count(r.rid) AS [rid count] " +
                         "FROM EPR, P, R " +
                         "WHERE p.pid=" + pid.get(i) + " and r.rid=18 and p.pid=epr.pid and r.rid=epr.rid " +
                         "GROUP BY pname, p.pid, rname, r.rid " +
                         "HAVING count(r.rid)>=1;";
         
         String query2 = "SELECT pname, p.pid, rname, r.rid, count(r.rid) AS [rid count] " +
                         "FROM EPR, P, R " +
                         "WHERE p.pid=" + pid.get(i) + " and r.rid=41 and p.pid=epr.pid and r.rid=epr.rid " +
                         "GROUP BY pname, p.pid, rname, r.rid " +
                         "HAVING count(r.rid)>=1;";
         
         ResultSet rs1 = stmt1.executeQuery(query1);
         ResultSet rs2 = stmt2.executeQuery(query2);
         
         if (rs1.next()) { programmerCount = rs1.getInt("rid count"); }
         if (rs2.next()) { assemblerCount = rs2.getInt("rid count"); }
         
         if (programmerCount > assemblerCount)
         {
            System.out.println("***** Constraint 2 Violation:");
            System.out.println("Project " + pid.get(i)+ " (" + pname.get(i) + ")");
            System.out.println("has more programmers (" + programmerCount +
                               ") than assemblers (" + assemblerCount + ").");
         }
         rs2.close();
         rs1.close();
      }
      stmt2.close();
      stmt1.close();
   }
//------------------------------------------------------------------------------
   private static void constraintThree (Connection con, ArrayList<Integer> pid, ArrayList<String> pname) throws Exception
   {
      Statement stmt1 = con.createStatement();
      Statement stmt2 = con.createStatement();
      Statement stmt3 = con.createStatement();
      for (int i = 0; i < pid.size(); i++)
      {
         int managerCounter = 0;
         double managerSalary = 0.0;
         double highestSalary = 0.0;
         String managerName = "";
         String managerEID = "";
         String subordinateName = "";
         String subordinateEID = "";
         
         String query1 = "SELECT pname, p.pid, count(rid) AS [count] " +
                         "FROM EPR, P "+
                         "WHERE p.pid=" + pid.get(i) + " and rid=22 and p.pid=epr.pid " +
                         "GROUP BY pname, p.pid HAVING count(rid)>=1;";
         
         String query2 = "SELECT DISTINCT E1.eid,E1.ename, E1.salary " +
                         "FROM E as E1 WHERE salary = " +
                         "(SELECT MAX(E2.salary) FROM E as E2,P,EPR " +
                         "WHERE EPR.EID=E2.EID and P.PID=EPR.PID and P.PID=" + pid.get(i) + " and EPR.RID<>22);";
         
         String query3 = "SELECT DISTINCT E1.eid,E1.ename, E1.salary " +
                         "FROM E as E1 WHERE salary = " +
                         "(SELECT MAX(E2.salary) FROM E as E2,P,EPR " +
                         "WHERE EPR.EID=E2.EID and P.PID=EPR.PID and P.PID=" + pid.get(i) + " and EPR.RID=22);";
         
         ResultSet rs1 = stmt1.executeQuery(query1);
         ResultSet rs2 = stmt2.executeQuery(query2);
         ResultSet rs3 = stmt3.executeQuery(query3);
         
         if (rs1.next()) { managerCounter = rs1.getInt("count"); }
         if (rs2.next()) 
         {
            highestSalary = (double)rs2.getInt("SALARY");
            subordinateName = rs2.getString("ENAME");
            subordinateEID = rs2.getString("EID");
         }
         if (rs3.next())
         {
            managerSalary = (double)rs3.getInt("SALARY");
            managerName = rs3.getString("ENAME");
            managerEID = rs3.getString("EID");
         }
         
         if (managerCounter != 1) {}
         else if (managerCounter == 1 && (managerSalary/highestSalary)*100 < 125.0)
         {
            System.out.println("\n***** Constraint 3 Violation:");
            System.out.println("Project " + pid.get(i)+ " (" + pname.get(i) + ")");
            System.out.println("Manager " + managerEID + "(" + managerName + ") earns $" + managerSalary);
            System.out.println("Subordinate " + subordinateEID + "(" + subordinateName + ") earns $" + highestSalary);
            System.out.println("Ratio is only " + (managerSalary/highestSalary)*100 + "%");
         }
         rs3.close();
         rs2.close();
         rs1.close();
      }
      stmt3.close();
      stmt2.close();
      stmt1.close();
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
        int pID = rs.getInt("PID");
        String pName = rs.getString("PNAME");
        System.out.println(pID+"\t"+pName);
      }
      
      rs = stmt.executeQuery("select * from R");
      
      System.out.println("\nRID\t"+"RNAME\t");
      while (rs.next())
      {
        int rID = rs.getInt("RID");
        String rName = rs.getString("RNAME");
        System.out.println(rID+"\t"+rName);
      }
      
      rs = stmt.executeQuery("select * from EPR");
      
      System.out.println("\nEID\t"+"PID\t"+"RID");
      while (rs.next())
      {
         String eID = rs.getString("EID");
         int pID = rs.getInt("PID");
         int rID = rs.getInt("RID");
         System.out.println(eID+"\t"+pID+"\t"+rID);
      }
      System.out.println();
      
      rs.close();
      stmt.close();
   }
//------------------------------------------------------------------------------
} // end class HW4
////////////////////////////////////////////////////////////////////////////////
