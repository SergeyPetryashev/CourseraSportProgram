package week2;

import java.io.*;

/*
 * 
 */
public class CarsAndPetrolStantion {

	public static void main(String[] args) throws IOException {
		BufferedReader brAzs = new BufferedReader(new FileReader(new File("inputCars.txt")));
		
		String [] str=brAzs.readLine().split(" ");
		int distanseFullTank = Integer.parseInt(str[2]);
		int fullDistanse = Integer.parseInt(str[1]);
		int [] distanseAZS = new int[Integer.parseInt(str[0])];
		str=brAzs.readLine().split(" ");
		
		for(int i=0; i<str.length; i++) {
			distanseAZS[i]=Integer.parseInt(str[i]);
		}
		solvePetrolStantion(fullDistanse, distanseFullTank, distanseAZS);
	}
	
	private static void solvePetrolStantion(int FullDist, int distanseFullTank, int [] distAZS) {
		int currentMaxDist=distanseFullTank;
		int count=0;

		int j=0;
		while(currentMaxDist<FullDist) {			
			if(currentMaxDist>distAZS[j] && j!=distAZS.length-1) {
				j++;
				continue;				
			}
			
			if(currentMaxDist==distAZS[j] || (j==distAZS.length-1 && currentMaxDist<FullDist)) {
				currentMaxDist=distAZS[j]+distanseFullTank;
				count++;
			}else if(currentMaxDist<distAZS[j]){
				currentMaxDist=distAZS[j-1]+distanseFullTank;
				count++;
			}			
			j++;
		}
		System.out.println(count);
	}
}
