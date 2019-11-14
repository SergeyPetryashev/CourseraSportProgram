package week3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ArithmEquation {

	public static void main(String[] args) throws IOException {
		 BufferedReader br = new BufferedReader(new FileReader(new File("inputWeek3/inputArithm.txt")));
	        String [] str = br.readLine().split(" "); 
	        int numTerms = Integer.parseInt(str[0]);
	        int target = Integer.parseInt(str[1]);
	        str = br.readLine().split(" ");
        	int [] terms = new int [numTerms];
        	int maxSum=0;
	        for(int j=0; j<numTerms;j++) {
        		terms[j]=Integer.parseInt(str[j]);
        		maxSum+=terms[j];
        	}
	        int plusSum = (maxSum-target)/2;
	        br.close();
	        solve(target, terms);
	}
	
	
	private static void solve(int target, int [] terms) {
		int [][] dp = new int [terms.length+1][target+1];
		for(int i=1; i<=terms.length;i++) {
			for(int j=0; j<=target; j++) {
				dp[i][j]=dp[i-1][j];
				if(j-terms[i-1]>=0 && 
						dp[i-1][j-terms[i-1]]+terms[i-1]==j) {
					dp[i][j]=dp[i-1][j-terms[i-1]]+terms[i-1];
				}
			}
		}
		System.out.println(dp[terms.length][target]);
	}
	
	

}
