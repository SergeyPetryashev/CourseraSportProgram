package Final;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        int [] stones=new int [numStone];
        str = br.readLine().split(" ");
        for(int i=0; i<numStone; i++) {
        	stones[i] = Integer.parseInt(str[i]);
        }        
        br.close();
        solveStones(stones);	// решение не оптимально
        
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
		for(int i=0; i<works.length;i++) {
			for(int j=i+1; j<works.length; j++) {
				time[i][j]=time[i][j-1];
				if(works[i].deadLine>=time[i][j]) {
					time[i][j]+=works[i].timeDuration;
					count[i]++;
				}
			}
		}
		System.out.println(Arrays.toString(count));
	}

	// Задача о камнях/ кучках золота
	private static void solveStones(int[] stones) {
		long sum1=0;
		long sum2=0;
		List<Integer> st1 = new ArrayList<>();
		List<Integer> st2 = new ArrayList<>();
		Arrays.sort(stones);
		for(int i=stones.length-1; i>=0;i--) {
			if(sum1==sum2 || sum1<sum2) {
				sum1+=stones[i];
				st1.add(stones[i]);
			}else {
				sum2+=stones[i];
				st2.add(stones[i]);
			}
		}
		System.out.println(st1);
		System.out.println(st2);
		System.out.println((int)Math.abs(sum1-sum2));
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
