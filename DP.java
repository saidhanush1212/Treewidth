import java.util.*;

public class DP
{
    public  int treewidth;
	public  int n,m;
	public  int memory; 
	public  HashMap<BitSet,Integer>[] dp;
	public  int[] color;
	public  int[] v2cc, cc2qvalue;
	public  ArrayList<Integer>[] G ;

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
		color = new int[n];
		v2cc = new int[n];		
		dp = new HashMap[n+1];
		dp[0] = new HashMap<BitSet,Integer>();
		dp[0].put(new BitSet(n),Integer.MIN_VALUE);
		for (int i = 1; i <= n; ++i)
		{
			//System.out.println("Starting with sets of size "+i);
			dp[i] = new HashMap<BitSet,Integer>(Math.max(dp[i-1].size(),n));
			if (i > 2){ dp[i-2] = null; }
			for (BitSet bitset: dp[i-1].keySet())
			{
				int firstbitset = bitset.nextSetBit(0);
				if (firstbitset == -1){ firstbitset = n; }
				for (int j = bitset.nextClearBit(0); j >= 0 && j<n; j=bitset.nextClearBit(j+1))
				{
				  bitset.set(j);
				  //System.out.println(set);
				  if(!dp[i].containsKey(bitset))
				  {
                      int rvalue = computeValue(bitset,i);
					  BitSet toStore = (BitSet) bitset.clone();
					  dp[i].put(toStore,rvalue);     
				  }
				  bitset.clear(j);
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

	private int computeValue(BitSet bitset, int size){
		int minValue = Integer.MAX_VALUE;
		
		bitset_allCC(bitset,size);//
		
		for (int i = bitset.nextSetBit(0); i >= 0; i=bitset.nextSetBit(i+1)){
			
			bitset.clear(i);
			
			if (dp[size-1].containsKey(bitset)){
				int prevTW = dp[size-1].get(bitset);
				
				int thisTW = Math.max(prevTW,cc2qvalue[v2cc[i]]);
				if (thisTW < minValue){
					minValue = thisTW;
					
				}
			}
			bitset.set(i);
		}
		
		return minValue;
	}

	private void bitset_allCC(BitSet bitset, int size){
		
		for(int i = 0; i < n; ++i){
			color[i] = 0;
			v2cc[i] = 0;
		}
		
		cc2qvalue = new int[size+1];
		int start = 0;
		int cc = 0;
		boolean done = false;
		while (!done){
			int bit = bitset.nextSetBit(start);
			if (bit == -1){
				done = true;
			} else if (color[bit] == 0) {
				++cc;
				cc2qvalue[cc] = computeQValue_oneCC(bitset,bit,color,cc);
			}
			start = bit+1;
		}
	}

	private int computeQValue_oneCC(BitSet bitset, int vertex, int[] color, int cc){
		int q = 0;
		color[vertex] = cc;
		v2cc[vertex] = cc;
		for (int neighbor : G[vertex]){
			if (color[neighbor] != cc){
				if (bitset.get(neighbor)){
					q = q + computeQValue_oneCC(bitset,neighbor,color,cc);
				} else {
					color[neighbor] = cc;
					++q;
				}
			}
			
		}
		return q;
	}

	
	



}