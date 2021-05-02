import java.util.*;

public class DP
{
    protected  int treewidth;
	protected  int n,m;
	protected  int memory; 
	protected  HashMap<BitSet,Integer>[] dp;
	protected  int[] qvalues, visited1, oldqvalues;
	protected  int[] vertex2cc, cc2q;
	protected  HashSet<Integer> cStar;
	protected  ArrayList<Integer>[] G ;

	DP(ArrayList<Integer>[] G ,int n,int m)
	{
		this.G=G;
		this.treewidth = n-1;
		this.n=n;
		this.m=m;
		System.out.println("Initialization Function");
		this.memory=0;
		
	}

    public void run() {
		System.out.println("Running algorithm Function");
		visited1 = new int[n];
		vertex2cc = new int[n];		
		dp = new HashMap[n+1];
		dp[0] = new HashMap<BitSet,Integer>();
		dp[0].put(new BitSet(n),Integer.MIN_VALUE);
		//Visit the sets in increasing size
		//System.out.println("n"+n);
		for (int i = 1; i <= n; ++i)
		{
			//System.out.println("Starting with sets of size "+i);
			dp[i] = new HashMap<BitSet,Integer>(Math.max(dp[i-1].size(),n));
			if (i > 2){ dp[i-2] = null; }
			for (BitSet set: dp[i-1].keySet())
			{
				int firstbitset = set.nextSetBit(0);
				if (firstbitset == -1){ firstbitset = n; }
				for (int j = set.nextClearBit(0); j >= 0 && j<n; j=set.nextClearBit(j+1))
				{
				  set.set(j);
				  //System.out.println(set);
				  if(!dp[i].containsKey(set))
				  {
                      int rPrime = computeValue(set,i);
					  BitSet toStore = (BitSet) set.clone();
					  dp[i].put(toStore,rPrime);     
				  }
				  set.clear(j);
				}	
			}
		
			//System.out.println("i "+i);
			//System.out.println(dp[i]);
			memory=Math.max(memory,dp[i-1].size()+dp[i].size());
		}
		int mini=Integer.MAX_VALUE;
			
		if (dp[n].values().toArray().length > 0)
			mini = (Integer) dp[n].values().toArray()[0];
		else
		  mini = n-1;
		treewidth=mini;   	
	}	

	private int computeValue(BitSet set, int size){
		int minValue = Integer.MAX_VALUE;
		//int vertexId;
		setSet(set,size);
		//For all vertices in the set
		for (int i = set.nextSetBit(0); i >= 0; i=set.nextSetBit(i+1)){
			//remove the vertex from the set
			set.clear(i);
			//If the remaining set of vertices could still contribute to a better upperbound
			if (dp[size-1].containsKey(set)){
				int prevTW = dp[size-1].get(set);
				//int q = q(set,new boolean[graph.vertices.size()],i);
				//int thisTW = Math.max(prevTW,q);
				int thisTW = Math.max(prevTW,cc2q[vertex2cc[i]]);
				if (thisTW < minValue){
					minValue = thisTW;
					//vertexId = i;
				}
			}
			//put the vertex back in the set
			set.set(i);
		}
		//System.out.print(" Computed value: "+minValue+"\n");
		return minValue;
	}

	private void setSet(BitSet set, int size){
		//compute q-values for the connected components
		//changedVertices = new ArrayList<Integer>();
		//changedValues = new ArrayList<Integer>();
		//qvalues = new int[graph.vertices.size()];
		for(int i = 0; i < n; ++i){
			visited1[i] = 0;
			vertex2cc[i] = 0;
		}
		//visited1 = new int[graph.vertices.size()];
		//vertex2cc = new int[graph.vertices.size()];
		cc2q = new int[size+1];
		int start = 0;
		int cc = 0;
		boolean done = false;
		while (!done){
			int bit = set.nextSetBit(start);
			if (bit == -1){
				done = true;
			} else if (visited1[bit] == 0) {
				++cc;
				cc2q[cc] = computeQValue(set,bit,visited1,cc);
			}
			start = bit+1;
		}
	}

	private int computeQValue(BitSet set, int vertex, int[] visited, int cc){
		int q = 0;
		visited[vertex] = cc;
		vertex2cc[vertex] = cc;
		for (int neighbor : G[vertex]){
			if (visited[neighbor] != cc){
				if (set.get(neighbor)){
					q = q + computeQValue(set,neighbor,visited,cc);
				} else {
					visited[neighbor] = cc;
					++q;
				}
			}
			/*
			if (!set.get(neighbor) && visited[neighbor] != cc){
				visited[neighbor] = cc;
				++q;
			} else if (set.get(neighbor) && visited[neighbor] != cc){
				q = q + computeQValue(set,neighbor,visited,cc);
			}*/
		}
		return q;
	}

	
	



}