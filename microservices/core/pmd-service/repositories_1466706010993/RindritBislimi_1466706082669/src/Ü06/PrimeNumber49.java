package �06;

public class PrimeNumber49 {

	
	public int value;
	public boolean test = false;
	
	public boolean isPrime2;
	
	public PrimeNumber49(int aValue2) {
		this.value = aValue2;
		if (isPrime(aValue2)) { //man muss nicht == true eingeben
			this.isPrime2 = true;
		}
		else {
			this.isPrime2 = false;
		}
	}
		
		
	public boolean isPrime(int aValue) {
		this.value = aValue;
		int teiler;
		if (aValue != 1) {
			teiler = 0;
			for (int z�hler = 1; z�hler <= aValue; z�hler++) {
				if (aValue % z�hler == 0) {
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
