package week3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DynamicProgram {

	public static void main(String[] args) throws IOException {
		twoFoots(10);
		threeFoots(10);

        BufferedReader br = new BufferedReader(new FileReader(new File("inputBugWay.txt")));
        String s;
        s=br.readLine();
        String [] str = s.split(" "); 
        int numRows = Integer.parseInt(str[0]);
        int numColomn = Integer.parseInt(str[1]);
        int [][] field = new int[numRows][numColomn];
        for(int i=0; i<numRows; i++) {
        	s=br.readLine();
        	str = s.split(" ");
        	for(int j=0; j<numColomn;j++) {
        		field[i][j]=Integer.parseInt(str[j]);
        	}
        }
        br.close();
        movingBug(field);

	}
	//способы раскладки доминошек по полю 2 х n 
	private static void twoFoots(int n) {
		int[] solve = new int [n+1];
		solve[0]=solve[1]=1;
		for(int i=2; i<n+1;i++) {
			solve[i]=solve[i-1]+solve[i-2];
		}
		System.out.println(Arrays.toString(solve));
		System.out.println(solve[n]);
	}	
	//способы раскладки триминошек по полю 3 х n 
	private static void threeFoots(int n) {
		int[] solve = new int [n+1];
		solve[0]=solve[1]=1;
		solve[2]=1;
		solve[3]=2;
		for(int i=4; i<n+1;i++) {
			solve[i]=solve[i-1]+solve[i-3];
		}
		System.out.println(Arrays.toString(solve));
		System.out.println(solve[n]);
	}
	// перемещение жучка по полю с максимальной суммой
	// либо вниз D либо вправо R
	private static void movingBug(int [][] field) {
		int n=field.length;
		int m=field[0].length;
		int [][] p = new int[n][m];
		int [][] sum = new int[n][m];
		for(int i=0; i<n;i++) {
			for(int j=0; j<m; j++) {
				sum[i][j]=field[i][j];
				if(i>0 && sum[i-1][j]+field[i][j]>sum[i][j]) {
					sum[i][j]=sum[i-1][j]+field[i][j];
					p[i][j]=0;
				}
				if(j>0 && sum[i][j-1]+field[i][j]>sum[i][j]) {
					sum[i][j]=sum[i][j-1]+field[i][j];
					p[i][j]=1;
				}				
			}
		}
		System.out.println(sum[n-1][m-1]);
		printWayBug(p, n-1, m-1);
	}
	
	private static void printWayBug(int[][] way, int i, int j) {
		if(i==0 && j==0)
			return;
		if(way[i][j]==0) {
			printWayBug(way, i-1, j);
			System.out.print('D');
		}
		if(way[i][j]==1) {
			printWayBug(way, i, j-1);
			System.out.print('R');
		}
	}
	
	

}
