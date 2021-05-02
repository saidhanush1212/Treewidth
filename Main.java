import java.util.*;
import java.io.File;

public class Main{


    public static void main(String[] args)
	{
		try{

                Scanner sc = new Scanner(new File("input.txt"));
                
                //Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
                int n=sc.nextInt();
                int m=sc.nextInt();
                ArrayList<Integer> G[] =new ArrayList [n];
                for (int i = 0; i < n; i++) {
                    G[i] = new ArrayList<Integer>();
                }
                while(sc.hasNextInt())
                {
                    int u,v;
                    u=sc.nextInt();
                    v=sc.nextInt();
                    u--;v--;
                    G[u].add(v);
                    G[v].add(u);
                }
                DP ma = new DP(G,n,m);
                long start = System.currentTimeMillis();
                ma.run();
                long end = System.currentTimeMillis();
                System.out.println("Running time for graph with " +n+ " vertices "+
                                            (end - start) + "ms");
                
                
                System.out.println("Treewidth"+ma.treewidth);
        }
        catch(Exception ex)
         {
            ex.printStackTrace();   
         }    
	}


}
 
