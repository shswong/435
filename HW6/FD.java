// Written by Stephen Wong
//
import java.util.Set;
import java.util.TreeSet;
////////////////////////////////////////////////////////////////////////////////
class FD
{
   private Set<Character> lhs;
   private Set<Character> rhs;
//------------------------------------------------------------------------------
   public FD ()
   {
      this.lhs = new TreeSet<Character>();
      this.rhs = new TreeSet<Character>();
   }
//------------------------------------------------------------------------------
   public void addLHS (String s) { lhs.add(s.charAt(0)); }
//------------------------------------------------------------------------------
   public void addRHS (String s) { rhs.add(s.charAt(0)); }
//------------------------------------------------------------------------------
   public Set<Character> getlhs () { return this.lhs; }
//------------------------------------------------------------------------------
   public Set<Character> getrhs () { return this.rhs; }
//------------------------------------------------------------------------------ 
} // end class FD
////////////////////////////////////////////////////////////////////////////////