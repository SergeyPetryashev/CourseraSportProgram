package week4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BitMask {

	public static void main(String[] args) throws IOException {
		// ѕечать всех подмножеств
		allSubsets(9);
		int n=5;
		int [] arr = new int [n];
		for(int i=0; i<n; i++) {
			arr[i]=i;
		}
		sumSubsets(n, arr);
		// задача комиво€жера
        BufferedReader br = new BufferedReader(new FileReader(new File("inputTaskKomi.txt")));
        int size = Integer.parseInt(br.readLine()); 
        int [][] len=new int [size][size];
        String str="";
        int j=0;
        while((str=br.readLine())!=null) {
        	String [] number =  str.split(" ");
        	for(int i=0; i<number.length; i++) {
        		len[j][i] = Integer.parseInt(number[i]);
        	}
        	j++;
        }
        br.close();
        wayKomivoyager(len);
        
        // задача паркет
        int heigth=5;
        int length=6;
        int mod =1000000000;
        System.out.println("«адача паркет");
        numberDominoLayout(Math.max(heigth, length), Math.min(heigth, length), mod); 
        
        // поиск количества единичных битов в числе
        br = new BufferedReader(new FileReader(new File("inputWeek4/inputNumBitIsNumber.txt")));
        int numNumber = Integer.parseInt(br.readLine()); 
        String [] number =  br.readLine().split(" ");
        System.out.println("подсчет количества ненулевых битов в числе");
        for(int i=0; i<numNumber; i++) {
        	 System.out.print(Integer.bitCount(Integer.parseInt(number[i]))+" ");
        }
        br.close();
	}
	
	private static void numberDominoLayout(int m, int n, int k) {
		int [][] d = new int [m+1][1<<n];
		d[0][0]=1;
		String format = "%"+n+"s";
		for(int i=0; i<m; i++) {
			for( int mask=0; mask<(1<<n); mask++) {
				for(int newMask=0; newMask<(1<<n);newMask++) {
					if(can(format, mask, newMask)) {
						d[i+1][newMask]+=d[i][mask];
						d[i+1][newMask]=d[i+1][newMask]%k;
					}
				}
			}
		}
		System.out.println(d[m][0]);		
	}

	private static boolean can(String format, int mask, int newMask) {
		String temp;
		if((mask & newMask)!=0) {	// сразу выбрасываем
			return false;
		}else {
			temp = String.format(format, Integer.toBinaryString((mask | newMask))).replace(' ', '0');
		}
		int even=0;
		for(int i=0; i<temp.length();i++) {
			if(temp.charAt(i)=='0') {
				even++;
			}else {
				if(even%2==0)
					even=0;
				else
					return false;
			}
		}
		if(even%2==0)
			return true;
		else
			return false;
	}

	private static void allSubsets(int n) {
		List<String> subset = new ArrayList<>();
		String s;
		for(int mask=0; mask<(1<<n); mask++) {
			s="";
			for(int i=0; i<n; i++) {
				if((mask & (1<<i))!=0) {
//					System.out.print(i+1 + " ");
					s+=(i+1)+" ";
				}
			}
//			System.out.println();
			s+="p";
			subset.add(s);
		}
		Collections.sort(subset);
		System.out.println(subset.get(364));
	}
	
	private static void sumSubsets(int n, int [] a) {
		int [] sum = new int [1<<n];
		for(int mask=0; mask<(1<<n); mask++) {
			for(int i=0; i<n; i++) {
				if((mask & (1<<i))!=0) {
					sum[mask]=sum[mask^(1<<i)]+a[i];
					break;
				}					
			}
		}
	//	System.out.println(Arrays.toString(sum));
	}
	
	private static void wayKomivoyager(int [][] len) {
		int n=len.length; // квадратна€ матрицаџ
		int [][] d = new int [1<<n][n];
		for(int mask=0; mask<(1<<n); mask++) {
			for(int i=0; i<n; i++) {
				d[mask][i]=Integer.MAX_VALUE;
			}
		}
		d[0][0]=0;
		for(int mask=0; mask<(1<<n); mask++) {
			for(int i=0; i<n; i++) {
				if(d[mask][i]==Integer.MAX_VALUE)
					continue;
				for(int j=0; j<n; j++) {
					if((mask & (1<<j))==0) {
						d[mask^(1<<j)][j] = Math.min(d[mask^(1<<j)][j], d[mask][i]+len[i][j]);
					}
				}
			}
		}
		System.out.println("ƒлина пути "+d[(1<<n)-1][0]);
	}
	
	private static void printWay(int [][] d) {
		int n=d[0].length; // размер массива
		int m=1<<n;			// количество масок
		for(int i=0; i<n; i++) {
			for(int mask = (1<<n)-1; mask>=0; mask--) {
				
			}
		}
	}
}
