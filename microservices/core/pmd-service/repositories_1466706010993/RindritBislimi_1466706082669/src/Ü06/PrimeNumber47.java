package �06;

public class PrimeNumber47 {

	static int primzahl;
	static boolean test = false;
	
	public static boolean isPrime(int num) {
		int teiler;
		if (num != 1) {
			teiler = 0;
			for (int z�hler = 1; z�hler <= num; z�hler++) {
				if (num % z�hler == 0) {
					teiler++;
				}
			}
			if (teiler == 2) {
				test = true;
				return test;
			}
		}
		return test;
	}
}
