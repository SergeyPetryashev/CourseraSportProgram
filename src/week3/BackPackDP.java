package week3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BackPackDP {

	public static void main(String[] args) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(new File("inputWeek3/inputBackPack.txt")));
			String [] data = br.readLine().split(" ");
			int numThings = Integer.parseInt(data[0]);
			int maxWeight = Integer.parseInt(data[1]);
			Thing [] things = new Thing [numThings];
			for(int i=0; i< numThings; i++) {
				data = br.readLine().split(" ");
				things[i] = new Thing(Integer.parseInt(data[1]), Integer.parseInt(data[0]));
			}
			solveBackpack(maxWeight, things);
	        br.close();

	}
	
	private static void solveBackpack(int w, Thing [] things) {
		int [][] dp = new int [things.length+1][w+1];
		for(int i=1; i<=things.length;i++) {
			for(int j=0; j<=w; j++) {
				dp[i][j]=dp[i-1][j];
				if(j-things[i-1].weight>=0 && 
						dp[i-1][j-things[i-1].weight]+things[i-1].cost>dp[i][j]) {
					dp[i][j]=dp[i-1][j-things[i-1].weight]+things[i-1].cost;
				}
			}
		}
		System.out.println(dp[things.length][w]);
		printCert(things, dp, things.length, w);
	}
	
	private static void printCert(Thing[] things, int [][] a, int i, int j) {
		if(a[i][j]==0) {
			return;
		}
		if(a[i-1][j]==a[i][j])
			printCert(things, a, i-1, j);
		else{
			printCert(things, a, i-1, j-things[i-1].weight);
			System.out.println(things[i-1]);
		}
		
	}

}


class Thing implements Comparable<Thing>{
	int cost;
	int weight;
	
	public Thing(int cost, int weight) {
		this.cost=cost;
		this.weight=weight;
	}

	@Override
	public int compareTo(Thing o) {
		return Double.compare(this.cost/(double)this.weight, o.cost/(double)o.weight)*(-1);// reverse;
	}
	@Override
	public String toString() {
		return "[" + cost + ", " + weight + "]";
	}
}
