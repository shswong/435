// Written by Stephen Wong
//
import java.util.*;
import java.io.*;
////////////////////////////////////////////////////////////////////////////////
class HW7
{
   private static ArrayList<FD> fda;
//------------------------------------------------------------------------------
   public static void main ( String [] args ) throws Exception
   {
      File f = new File(args[0]);
		Scanner s = new Scanner(f);
		fda = new ArrayList<FD>();
		Set<Character> attributes = new TreeSet<Character>();

		while (s.hasNext())
		{
		   String line = s.nextLine();
			System.out.println(line);
		   
		   if (line.equalsIgnoreCase("reset"))
			{
			   attributes.clear();
			   fda.clear();
				String str = s.nextLine();
				System.out.println(str);
				StringTokenizer st = new StringTokenizer(str);
				
				while (st.hasMoreTokens())
				{
				   attributes.add(st.nextToken().charAt(0));
				}
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
			
			if (line.equalsIgnoreCase("ck"))
			{
			   ArrayList<String> binary_subsets = new ArrayList<String>();
			   ArrayList<String> possibleCK = new ArrayList<String>();
				
				for (int i = 0; i < Math.pow(2, attributes.size()); i++)
				{
				   binary_subsets.add(Integer.toBinaryString(i));
				}
				
				for (int i = 0; i < binary_subsets.size(); i++)
				{
				   String binarySubsetTemp = binary_subsets.get(i);
				   TreeSet<Character> attributesCopy = new TreeSet<Character>();
				   attributesCopy.addAll(attributes);
	            Set<Character> subset = new TreeSet<Character>();
				   
				   for (int j = binarySubsetTemp.length()-1; j >= 0 ; j--)
				   {
				      char attributeTest = attributesCopy.pollLast();
				      
				      if (binarySubsetTemp.charAt(j) == '1')
				      {
				         subset.add(attributeTest);
				      }
				   }
				   
				   Set<Character> computeClosure = new TreeSet<Character>();
				   computeClosure.addAll(subset);
				   
				   computeClosure = computeClosure(computeClosure);
				   computeClosure = computeClosure(computeClosure);
				   
				   if (computeClosure.size() == attributes.size())
				   {
				      possibleCK.add(subset.toString());
				   }
				}
				
				int minCK = possibleCK.get(0).length();
				
				for (int i = 0; i < possibleCK.size(); i++)
				{
				   if (possibleCK.get(i).length() < minCK)
				   {
				      minCK = possibleCK.get(i).length();
				   }
				}
				
				for (int i = 0; i < possibleCK.size(); i++)
				{
				   if (possibleCK.get(i).length() == minCK)
				   {
				      System.out.println(possibleCK.get(i));
				   }
				}
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
} // end class HW7
////////////////////////////////////////////////////////////////////////////////