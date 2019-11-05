package week1;

import java.util.Stack;

/*Задано число n. Выдать все корректные последовательности круглых и квадратных скобок
 * в лексиграфическом порядке 
 * (<)<[<]
 */
public class Brackets {
	static int length=14;
	static char [] typeBr = {'(', ')', '[', ']'};
	static int count=0;
	public static void main(String[] args) {
		Stack<Character> chars= new Stack<>();
		generateBrackets("", 0, 0, 0, 0, chars);
	}
	
	private static void generateBrackets(String brackets, int openBr, int closeBr, int openBrRect, int closeBrRect, Stack<Character> chars) {
		if(brackets.length()==length && openBr==closeBr && openBrRect==closeBrRect) {
			count++;
			if(count==8233)
				System.out.println(count +" "+ brackets);			
			return;
		}
		if(brackets.length()==length ) {
			return;
		}
		if(openBr<length/2-openBrRect && openBr>=closeBr) {
			openBr++;
			chars.push(typeBr[0]);
			generateBrackets(brackets+typeBr[0],openBr, closeBr, openBrRect, closeBrRect, chars);
			openBr--;
			chars.pop();
		}
		if(closeBr<openBr && chars.peek()==typeBr[0]) {
			closeBr++;
			chars.pop();
			generateBrackets(brackets+typeBr[1],openBr, closeBr, openBrRect, closeBrRect, chars);
			closeBr--;
			chars.push(typeBr[0]);
		}
		if(openBrRect<length/2-openBr && openBrRect>=closeBrRect) {
			openBrRect++;
			chars.push(typeBr[2]);
			generateBrackets(brackets+typeBr[2],openBr, closeBr, openBrRect, closeBrRect, chars);
			openBrRect--;
			chars.pop();
		}
		if(closeBrRect<openBrRect && chars.peek()==typeBr[2]) {
			closeBrRect++;
			chars.pop();
			generateBrackets(brackets+typeBr[3],openBr, closeBr, openBrRect, closeBrRect, chars);
			closeBrRect--;
			chars.push(typeBr[2]);
		}
	}
}
