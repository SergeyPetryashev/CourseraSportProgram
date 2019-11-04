package week1;

import java.util.ArrayList;
import java.util.List;

public class BruteForce {
	static String data = "abcdefghijklmnopqrstuvwxyz";
	static boolean [] isChange;
	static List<Integer> term = new ArrayList<>();
	
	static int [][] a = {{0, 1, 2, 3},
						{2, 0, 4, 5},
						{2, 3, 0, 6},
						{4, 5, 3, 0}};
	static List<Integer> p = new ArrayList<>();
	static int ans = Integer.MAX_VALUE;
	static boolean [] used = new boolean[a.length];

	public static void main(String[] args) {
		int numberChar=3;
		int lengthWord=3;
		isChange = new boolean [lengthWord];
	//	recBruteForce(0,numberChar, new char [lengthWord]);
		System.out.println();
	//	recBruteForceTransposition(0, new char [lengthWord]);
		bracket(0, 0, new char[2*lengthWord], 0);
		numberToTerm(0, 0, 1, 5);// idx sum last target
		taskKomivoyger(0);
		System.out.println(ans);
	}
	
	private static void recBruteForce(int idx, int size, char[] word) {
		if(idx==word.length) {
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
			System.out.println(term);
			return;
		}
		for(int i=last; i<=target-sum; i++) {
			term.add(i);
			numberToTerm(idx+1, sum+i, i, target);
			term.remove(idx);
		}
	}
	
	private static void taskKomivoyger(int idx) {
		if(a.length==idx) {
			ans=Math.min(ans, count());
			System.out.println(p + " ans " + ans);
			return;
		}
		for(int i=0; i<a.length; i++) {
			if(i==idx)
				continue;
			if(used[i])
				continue;
			p.add(i);
			used[i]=true;
			taskKomivoyger(idx+1);
			used[i]=false;
			p.remove(idx);
			
		}
	}
	
	private static int count() {
		int sum=0;
		for(int i=0; i<p.size(); i++) {
			sum+=a[i][p.get(i)];
		}
		return sum;
	}
}
