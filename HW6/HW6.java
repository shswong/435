// Written by Stephen Wong
//
import java.util.*;
import java.io.*;
////////////////////////////////////////////////////////////////////////////////
class HW6
{
   private static ArrayList<FD> fda;
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      File f = new File(args[0]);
		Scanner s = new Scanner(f);
		fda = new ArrayList<FD>();

		while (s.hasNext())
		{
		   String line = s.nextLine();
			System.out.println(line);
		   
		   if (line.equalsIgnoreCase("reset"))
			{
			   fda.clear();
				String str = s.nextLine();
				System.out.println(str);
			}
			
			if (line.equalsIgnoreCase("fd"))
			{
			   FD fd = new FD();
			   String str = s.nextLine();
				System.out.println(str);
				StringTokenizer st = new StringTokenizer(str);
				boolean b = true;
				
				while (st.hasMoreTokens() && b)
				{
				   String test = st.nextToken();
					if (!(test.equals("->")))
					{
				      fd.addLHS(test);
					}
					else { b = false; }
				}
				
				while (st.hasMoreTokens())
				{
				   fd.addRHS(st.nextToken());
				}
				fda.add(fd);
			}
			
			if (line.equalsIgnoreCase("closure"))
			{
			   String str = s.nextLine();
				System.out.println(str);
				Set<Character> closureSet = new TreeSet<Character>();
		      StringTokenizer st = new StringTokenizer(str);
		            
		      while (st.hasMoreTokens())
		      {
		         closureSet.add(st.nextToken().charAt(0));
		      }
		      
				closureSet = computeClosure(closureSet);
				closureSet = computeClosure(closureSet);
				
				Iterator<Character> i = closureSet.iterator();
		      while (i.hasNext())
				{
		         System.out.print(i.next() + " ");
				}
		      System.out.println();
			}
		}
   }
//------------------------------------------------------------------------------
   private static Set<Character> computeClosure (Set<Character> closureSet)
	{
		for (int x = 0; x < fda.size(); x++)
		{
		   Set<Character> lhs = fda.get(x).getlhs();
		   if (closureSet.containsAll(lhs))
		   {
		      Set<Character> rhs = fda.get(x).getrhs();
            Iterator<Character> k = rhs.iterator();
                  
            while (k.hasNext())
            {
               closureSet.add(k.next());
            }
		   }
		}
		return closureSet;
	}
//------------------------------------------------------------------------------
} // end class HW6
////////////////////////////////////////////////////////////////////////////////