package week3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class DynamicProgram {

	public static void main(String[] args) throws IOException {
		System.out.println("Доминошки");
		twoFoots(4, 1);
		twoFoots(100000, 1000000000);
		System.out.println("Триминошки");
		threeFoots(10);
		System.out.println("Задача про жучка");
        BufferedReader br = new BufferedReader(new FileReader(new File("inputWeek3/inputBugWay.txt")));
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
        // подсуммы прямоугольников матрицы
        System.out.println("\nПодсуммы в прямоугольниках матрицы");
        br = new BufferedReader(new FileReader(new File("inputWeek3/inputMatrixParthSum.txt")));
        str = br.readLine().split(" "); 
        numRows = Integer.parseInt(str[0]);
        numColomn = Integer.parseInt(str[1]);
        int [][] matrix = new int[numRows][numColomn];
        for(int i=0; i<numRows; i++) {
        	s=br.readLine();
        	str = s.split(" ");
        	for(int j=0; j<numColomn;j++) {
        		matrix[i][j]=Integer.parseInt(str[j]);
        	}
        }
        int numRequest = Integer.parseInt(br.readLine());
        int [][] partSum = preCalculate(matrix);
        long sum=0;
        for(int i=0; i<numRequest; i++) {
        	s=br.readLine();
        	str = s.split(" ");
        	int answer=answerRequest(partSum,Integer.parseInt(str[0]),Integer.parseInt(str[1]),
        			Integer.parseInt(str[2]),Integer.parseInt(str[3])); 
    		//System.out.println(answer);
        	sum+=answer;
        }
        System.out.println(sum);
        br.close();
        
        // размен суммы минимальным количеством монет
        System.out.println("\nРазмен монет");
        br = new BufferedReader(new FileReader(new File("inputWeek3/inputChangeCoins.txt")));
        s=br.readLine();
        int price = Integer.parseInt(s);
        
        s=br.readLine();
        str = s.split(" ");
        int [] coins = new int [str.length];
        for(int i=0; i<str.length;i++) {
        	coins[i]=Integer.parseInt(str[i]);
        }
        changePriceToCoins(price, coins);
        br.close();
        
        // Наибольшая общая подпоследовательность
        System.out.println("\nНаибольшая общая подпоследовательность");
        br = new BufferedReader(new FileReader(new File("inputWeek3/inputSequence.txt")));
        br.readLine();
        str = br.readLine().split(" ");
        int [] seq1 = new int [str.length];
        for(int i=0; i<str.length;i++) {
        	seq1[i]=Integer.parseInt(str[i]);
        }
        br.readLine();
        str = br.readLine().split(" ");
        int [] seq2 = new int [str.length];
        for(int i=0; i<str.length;i++) {
        	seq2[i]=Integer.parseInt(str[i]);
        }
        findMaxJointSequence(seq1, seq2);
        br.close();
        
        // Наибольшая возрастающая подпоследовательность
        System.out.println("\nНаибольшая возрастающая подпоследовательность");
        br = new BufferedReader(new FileReader(new File("inputWeek3/inputUpSequence.txt")));
        int size =Integer.parseInt(br.readLine());
        str = br.readLine().split(" ");
        int [] seqUp = new int [size];
        for(int i=0; i<size;i++) {
        	seqUp[i]=Integer.parseInt(str[i]);
        }       
        System.out.println(Arrays.toString(seqUp));
        
        findMaxUpSequence(seqUp);
        br.close();
	}
	// Наибольшая возрастающая подпоследовательность
	private static void findMaxUpSequence(int[] seq) {
		int [] d = new int [seq.length];
		int maxLength = d[0];
		for(int i=0; i<seq.length;i++) {
			d[i]=1;;
			for(int j=0; j<i; j++) {
				if(seq[i]>seq[j]) {
					if(d[j]+1>d[i]) {
						d[i]=d[j]+1;
					}
				}					
			}
			if(d[i]>maxLength) {
				maxLength=d[i];
			}			
		}
		long [] maxSize = new long [seq.length+1];
		maxSize[0]=1;
		for(int i=0; i<seq.length; i++) {
			for(int j=1; j<=seq.length;j++) {
				if(j>maxLength)
					break;
				if(d[i]==j) {
						maxSize[j]+=maxSize[j-1];
						break;
				}
			}
		}
		
		System.out.println("Длина возрастающей подпоследовательности\n"+ maxLength);
		System.out.println("Количество " + maxSize[maxLength]);	// расчет не верный
	}
	// определение наибольшей общей подпоследовательности
	private static void findMaxJointSequence(int[] seq1, int[] seq2) {
		int [][] solve = new int [seq1.length+1][seq2.length+1];
		int [][] cert = new int [seq1.length+1][seq2.length+1];
		for(int i=1;i<=seq1.length;++i) {
			for(int j=1;j<=seq2.length;++j) {
				solve[i][j]=Math.max(solve[i-1][j], solve[i][j-1]);
				if(seq1[i-1]==seq2[j-1]) {
					solve[i][j]=Math.max(solve[i][j], solve[i-1][j-1]+1);
					cert[i][j]=seq1[i-1];
				}
			}
		}
		System.out.println(solve[seq1.length][seq2.length]);
	//	recPrintCertSequence(solve, cert, seq1.length, seq2.length);
	}
	private static void recPrintCertSequence(int[][] solve, int[][] cert, int indSeq1, int indSeq2) {
		if(indSeq1==0 && indSeq2==0)
			return;
		if(solve[indSeq1][indSeq2]==solve[indSeq1-1][indSeq2]) {
			recPrintCertSequence(solve, cert, indSeq1-1, indSeq2);
		}else if(solve[indSeq1][indSeq2]==solve[indSeq1][indSeq2-1]){
			recPrintCertSequence(solve, cert, indSeq1, indSeq2-1);
		}else {
			recPrintCertSequence(solve, cert, indSeq1-1, indSeq2-1);
			System.out.print(cert[indSeq1][indSeq2]);
			if(indSeq1!=cert.length-1 && indSeq2!=cert[0].length-1) {
				System.out.print(", ");
			}
		}		
	}
	
	// размен суммы минимальным количеством монет
	private static void changePriceToCoins(int price, int[] coins) {
		int [] res = new int [price+1];// размен суммы минимальным количеством монет;
		int [] p = new int [price+1];
		for (int i=1; i<=price; i++) {
			res[i]=Integer.MAX_VALUE;
			for(int j=0; j<coins.length;j++) {
				if(i-coins[j]>=0 && res[i-coins[j]]+1<res[i]) {
					res[i]=res[i-coins[j]]+1;
					p[i]=coins[j];
				}
			}
		}
		System.out.println(res[price]);
		recout(p, price);
		
	}
	private static void recout(int[] p, int index) {
		if(index==0)
			return;
		recout(p, index-p[index]);
		if(index-p[index]>0)
			System.out.print("+");
		System.out.print(p[index]);
	}
	// расчет подсуммы элементов матрицы
	private static int[][] preCalculate(int[][] matrix) {
		int n = matrix.length;
		int m= matrix[0].length;
		int [][] result = new int[n+1][m+1];
		for(int i=1; i<n+1; i++) {
			for(int j=1; j< m+1; j++) {
				result[i][j] = result[i-1][j]+result[i][j-1] - result[i-1][j-1] + matrix[i-1][j-1];
			}
		}
		return result;
	}
	
	private static int answerRequest(int[][] arr, int startX, int endX, int startY, int endY) {
		int answer = arr[endX][endY]-arr[startX-1][endY] - arr[endX][startY-1] + arr[startX-1][startY-1];
		return answer;
	}
	//способы раскладки доминошек по полю 2 х n 
	private static void twoFoots(int n, int m) {
		int[] solve = new int [n+1];
		solve[0]=solve[1]=1;
		int[] solveFull = new int [n+1];
		solveFull[0]=solveFull[1]=1;
		
		for(int i=2; i<n+1;i++) {
			solve[i]=(solve[i-1]+solve[i-2])%m;
			solveFull[i]=solveFull[i-1]+solveFull[i-2];
		}
//		System.out.println(Arrays.toString(solve));
		System.out.println("full " + solveFull[n]);
		System.out.println("solve%m=" + solve[n]);
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
//		System.out.println(Arrays.toString(solve));
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
