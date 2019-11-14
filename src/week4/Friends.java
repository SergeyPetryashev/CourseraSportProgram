package week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Friends {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File("inputWeek4/inputFriends.txt")));
        String [] str=br.readLine().split(" ");
        int numFriends = Integer.parseInt(str[0]);
        int numLinks = Integer.parseInt(str[1]);
        int [][] links = new int [numFriends][numFriends];
        int [] bitLink = new int [numFriends];
        for(int i=0; i<numLinks; i++) {
        	str=br.readLine().split(" ");
        	links[Integer.parseInt(str[0])-1][Integer.parseInt(str[1])-1] = 1;
        	links[Integer.parseInt(str[1])-1][Integer.parseInt(str[0])-1] = 1;
        	bitLink[Integer.parseInt(str[0])-1]=bitLink[Integer.parseInt(str[0])-1] | (1<<(numFriends-Integer.parseInt(str[1])));
        	bitLink[Integer.parseInt(str[1])-1]=bitLink[Integer.parseInt(str[1])-1] | (1<<(numFriends-Integer.parseInt(str[0])));
        }
        String format = "%"+numFriends+"s";
        for(int i : bitLink) {
        	System.out.println(String.format(format, Integer.toBinaryString(i)).replace(' ', '0'));
        }
        int mask=(1<<numFriends)-1;
        int[][] newMask=new int [numFriends][numFriends];
        int[][] count=new int [numFriends][numFriends];
        
        for(int i=0; i<numFriends; i++) {
        	for(int j=0; j<numFriends; j++) {
        		if(i==j) {
        			newMask[i][j]=bitLink[i];
        			continue;
        		}
        		if((bitLink[i] & 1<<(numFriends-1-j))!=0) {
        			newMask[i][j]=bitLink[i] & bitLink[j];
        			newMask[j][i]=newMask[i][j];
        			count[i][j]++;
        			count[j][i]++;
        		}
        	}
        }
        br.close();
        int max=0;
        for(int i=0; i<numFriends; i++) {
	    	for(int j=i; j<numFriends; j++) {
	    		if(max<count[i][j]) {
	    			max=count[i][j];
	    		}
	    	}
        }
        System.out.println(max+1);

	}

}
