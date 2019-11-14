package week3;

public class BinomCoefficient {

	public static void main(String[] args) {
		int n=50;
		int k=20;
		System.out.println(calculateBinomCoeff(n, k));
		System.out.println(calculate(n, k));

	}

	private static long calculateBinomCoeff(int n, int k) {
		long [][] cnk = new long [n+1][k+1];
		for(int i=0; i<=n; i++) {
			cnk[i][0]=1;
		}		
		
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=k; j++) {
				if(i==j) {
					cnk[i][j]=1;
				}else if(j<i){
					cnk[i][j]=cnk[i-1][j-1]+cnk[i-1][j];
				}
			}
		}		
		return cnk[n][k];
	}
	
	private static long calculate(int n, int k) {
		long power=1;
		int count=1;
		for(int i=k+1; i<=n; i++) {
			power*=i;
			if(power%count==0 && count<=n-k) {
				power/=count;
				count++;
			}
		}
		return power;
	}

}
