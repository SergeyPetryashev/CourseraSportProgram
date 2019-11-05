package week1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteForce {
	static String data = "123456789abcdefghijklmnopqrstuvwxyz";
	static boolean [] isChange;
	static List<Integer> term = new ArrayList<>();
	
	static int [][] a;
	static int[] p;
	static int ans = Integer.MAX_VALUE;
	static int[] minWay;
	static boolean [] used;
	static int countTask1=0;
	static int countTask2=0;
	static int countTask3=0;
	static int countBracket=0;
	static int countStartAndPoint=0;

	public static void main(String[] args) throws IOException {
		int numberChar=4;
		int lengthWord=10;
		isChange = new boolean [lengthWord];
		recBruteForce(0,numberChar, new char [lengthWord]);
		System.out.println();
		recBruteForceTransposition(0, new char [lengthWord]);
		bracket(0, 0, new char[2*lengthWord], 0);
		numberToTerm(0, 0, 1, 35);// idx sum last target

		
        File file = new File("inputTaskKomi.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int size = Integer.parseInt(br.readLine()); 
        a=new int [size][size];
        String str="";
        int j=0;
        while((str=br.readLine())!=null) {
        	String [] number =  str.split(" ");
        	for(int i=0; i<number.length; i++) {
        		a[j][i] = Integer.parseInt(number[i]);
        	}
        	j++;
        }
        br.close();
        
		p = new int [size];
//		minWay = new int [size];
		used = new boolean[size];        
		taskKomivoyger(1, 0);
		System.out.println("Way: " +Arrays.toString(minWay) + " Length way " +ans);
		
		int numStar=8;
		int maxLength=25;
		recGenerateStringStartAndPoint("", numStar, maxLength);
	}
	
	private static void recBruteForce(int idx, int size, char[] word) {
		if(idx==word.length) {
			++countTask1;
			if(countTask1==6659)
				System.out.println(word);
			return;
		}
		for(int i=0; i<size; i++){
			word[idx] = data.charAt(i);
			recBruteForce(idx+1, size, word);
		}
	}
	
	private static void recBruteForceTransposition(int idx, char[] word) {
		if(idx==word.length) {
			if(++countTask2==4468)
				System.out.println(word);
			return;
		}
		for(int i=0; i<word.length; i++){
			if(isChange[i])
				continue;
			word[idx] = data.charAt(i);
			isChange[i]=true;
			recBruteForceTransposition(idx+1, word);
			isChange[i]=false;
		}
	}
	
	private static void bracket(int leftBrackets, int rightBrackets, char [] brackets, int idx) {
		if(idx==brackets.length) {
			countBracket++;
			if(countBracket==8644)
				System.out.println(brackets);
			return;
		}
		if(leftBrackets<brackets.length/2) {
			brackets[idx]='(';
			bracket(leftBrackets+1, rightBrackets, brackets, idx+1);
		}
		if(leftBrackets>rightBrackets) {
			brackets[idx]=')';
			bracket(leftBrackets, rightBrackets+1, brackets, idx+1);			
		}
	}
	
	private static void numberToTerm(int idx, int sum, int last, int target) {
		if(sum==target) {
			if(++countTask3==13672)
				System.out.println(term);
			return;
		}
		for(int i=last; i<=target-sum; i++) {
			term.add(i);
			numberToTerm(idx+1, sum+i, i, target);
			term.remove(idx);
		}
	}
	
	private static void taskKomivoyger(int idx, int len) {
		if(len>=ans)
			return;
		if(idx==a.length) {
			if(ans>len+a[p[idx-1]][0]) {
				ans=len+a[p[idx-1]][0];
				minWay=Arrays.copyOf(p, p.length);
			}
			return;
		}
		for(int i=1; i<a.length; i++) {
			if(used[i])
				continue;
			p[idx]=i;
			used[i]=true;
			taskKomivoyger(idx+1, len+a[p[idx-1]][i]);
			used[i]=false;
		}
	}
	
	private static int count() {
		int sum=0;
		int count=0;
		int i=0;
		while(count<p.length) {
			sum+=a[i][p[count]];
			i=p[count++];
		}
		return sum;
	}
	
	private static void recGenerateStringStartAndPoint(String current, int numStar, int maxLength) {
		if(current.length()==maxLength && numStar==0) {
			if(++countStartAndPoint==24008)
				System.out.println(current);
			return;
		}
		if((maxLength-current.length())%2==0 && numStar>(maxLength-current.length())/2)
			return;
		if((maxLength-current.length())%2==1 && numStar>(maxLength-current.length())/2+1)
			return;
		
		if(numStar>0 && (current.isEmpty() || current.charAt(current.length()-1)=='.')) {
			recGenerateStringStartAndPoint(current+"*", numStar-1, maxLength);			
		}
		recGenerateStringStartAndPoint(current+".", numStar, maxLength);
	}
}
