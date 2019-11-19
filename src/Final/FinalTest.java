package Final;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class FinalTest {
	static int count=0; 
	public static void main(String[] args) throws IOException{
		//Шаблон
		System.out.println("Pattern ");
		String s = "?be?bdcb?dcb?debcd??bdad?ee";
		String set = "abcde";
		pattern(s, set, 0);
		
		// магазины и склады
		System.out.println("Shops and storage");
        BufferedReader br = new BufferedReader(new FileReader(new File("inputShopsAndStorage.txt")));
        int size = Integer.parseInt(br.readLine()); 
        int [] shops=new int [size];
        int [] storage=new int [size];
        String [] str = br.readLine().split(" ");
        for(int i=0; i<size; i++) {
        	shops[i] = Integer.parseInt(str[i]);
        }
        str = br.readLine().split(" ");
        for(int i=0; i<size; i++) {
        	storage[i] = Integer.parseInt(str[i]);
        }
        br.close();
        Arrays.sort(shops);
        Arrays.sort(storage);
        long sum=0;
        for(int i=0; i<size; i++) {
        	sum +=(int)Math.abs(shops[i]-storage[i]);
        }
        System.out.println(sum);
        
        // задача о камнях
        System.out.println("The stones");
        br = new BufferedReader(new FileReader(new File("inputStones.txt")));
        int numStone = Integer.parseInt(br.readLine()); 
        long [] stones=new long [numStone];
        str = br.readLine().split(" ");
        long sumWeigthStone=0;
        long min=Long.MAX_VALUE;
        for(int i=0; i<numStone; i++) {
        	stones[i] = Long.parseLong(str[i]);
        	//stones[i] = Integer.parseInt(str[i]);
        	if(min>stones[i])
        		min=stones[i];
        	sumWeigthStone+=stones[i];
        }        
        br.close();
        System.out.println("Честный дележ");

   /*     long oneGold=solveHeapGold(stones, (int)(sumWeigthStone/2));
        long twoGold=sumWeigthStone-oneGold;
        System.out.println("Вторая кучка " + twoGold);
        System.out.println("Разность "+ Math.abs(twoGold-oneGold));//*/

        System.out.println("Перебор");
        bitBruteForce(stones, sumWeigthStone);
        
        System.out.println("Нечестный дележ");
        Arrays.sort(stones);
        long s1=0;
        long s2=0;
        for(int i=0; i<stones.length/2; i++) {
        	s1+=stones[i];
        	s2+=stones[stones.length/2+i];        	
        }
        System.out.println("Разность "+ Math.abs(s1-s2));

        // Тайм менеджмент
        System.out.println("The time");
        br = new BufferedReader(new FileReader(new File("inputTime.txt")));
        int numWorks = Integer.parseInt(br.readLine()); 
        TimeTable [] works=new TimeTable [numWorks];
        for(int i=0; i<numWorks; i++) {
        	str = br.readLine().split(" ");
        	works[i] = new TimeTable(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
        }
        System.out.println(Arrays.toString(works));
        br.close();
        solveTimeTable(works);	// решение не доработано
        
	}
	// Тайм менеджмент
	private static void solveTimeTable(TimeTable[] works) {
		int [] count = new int [works.length];
		int [][] time = new int [works.length+1][works.length+1];
		for(int i=1; i<=works.length; i++) {
			time[0][i]=Integer.MAX_VALUE;
		}
		int max=0;
		for(int i=1; i<=works.length;i++) {
			for(int j=1; j<=works.length; j++) {
				if(works[i-1].deadLine>=time[i-1][j-1] && j<=i) {
					time[i][j]=Math.min(time[i-1][j], time[i-1][j-1]+works[i-1].timeDuration);
					count[i-1]++;
				}else {
					time[i][j]=time[i-1][j];
				}
			}
			max=Math.max(max, count[i-1]);
		}
		System.out.println(max);	
	}

	// Задача о камнях/ кучках золота для небольших весов
	private static long solveHeapGold(int [] stones, int target) {
		int [][] dp = new int [stones.length+1][target+1];
		for(int i=1; i<=stones.length;i++) {
			for(int j=0; j<=target; j++) {
				dp[i][j]=dp[i-1][j];
				if(j-stones[i-1]>=0 && 
						dp[i-1][j-stones[i-1]]+stones[i-1]>dp[i][j] &&
						dp[i-1][j-stones[i-1]]+stones[i-1]<=target) {
					dp[i][j]=dp[i-1][j-stones[i-1]]+stones[i-1];
				}
			}
		}
		System.out.println("Первая кучка " +dp[stones.length][target]);
		printCert(stones, dp, stones.length, target);
		return dp[stones.length][target];
	}
	
	private static void printCert(int [] stones, int [][] a, int i, int j) {
		if(a[i][j]==0) {
			return;
		}
		if(a[i-1][j]==a[i][j]) {
			printCert(stones, a, i-1, j);
		}else{
			printCert(stones, a, i-1, j-stones[i-1]);
			System.out.print(i+" ");
		}
	}
	// решение при больших числах перебор рекурсией
	private static void bitBruteForce(long [] stone, long sum) {
		int n=stone.length;
		String format="%"+n+"s";
		long [] dp = new long[(1<<n)];
		long minDelta = Long.MAX_VALUE;
		int maskS1=0;
		int idx=-1;
		int lastLength=0;
		for(int mask=1; mask<(1<<n); mask++) {
			if(Integer.toBinaryString(mask).length()>lastLength) {
				idx++;
				lastLength=Integer.toBinaryString(mask).length();
			}
			int addMask = mask ^ (1<<idx);
			dp[mask]=stone[idx]+dp[addMask];
			if(minDelta>Math.abs(sum/2-dp[mask])) {
				minDelta=Math.abs(sum/2-dp[mask]);
				maskS1=mask;
			}
		}
		int maskS2=maskS1^(1<<n)-1;
		System.out.println("Разность " +Math.abs(dp[maskS1]-dp[maskS2]));
		System.out.println(String.format(format, Integer.toBinaryString(maskS1)).replace(' ', '0'));
		System.out.println(String.format(format, Integer.toBinaryString(maskS2)).replace(' ', '0'));
	}
	
// задача о шаблонах
	private static void pattern (String s, String set, int index) {
		if(index==s.length()) {
			count++;
			if(count==5151)
				System.out.println(s);
			return;
		}
		StringBuilder str = new StringBuilder(s);
		if(str.charAt(index)=='?') {
			for(int j=0; j<set.length(); j++) {
				str.setCharAt(index,set.charAt(j));
				pattern (str.toString(), set, index+1);
			}
		}else {
			pattern (str.toString(), set, index+1);
		}
	}
}

class TimeTable implements Comparable<TimeTable>{
	int timeDuration;
	int deadLine;
	public TimeTable(int timeDuration, int deadLine) {
		this.timeDuration=timeDuration;
		this.deadLine=deadLine;
	}

	@Override
	public int compareTo(TimeTable o) {
		return Integer.compare(this.timeDuration,o.timeDuration);
	}

	@Override
	public String toString() {
		return "[" + timeDuration + ", " + deadLine + "]";
	}
}
