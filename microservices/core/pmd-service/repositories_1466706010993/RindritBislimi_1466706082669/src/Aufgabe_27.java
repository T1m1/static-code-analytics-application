
import java.util.Scanner;

public class Aufgabe_27 {

	public static void main(String[] args) {
		
		Scanner s = new Scanner (System.in);
	
		
		byte x;
		
		do{
			System.out.println("Geben Sie eine Feldgr��e an");
			x=s.nextByte();	
		}
		while(x<1);
		
		int[] array = new int [x];
		int y;
		int sum = 0;
		
		for(int z = 0; z < array.length; z++)	{
			y = (int)(Math.random() * 201) -100;
			array [z] = y;
			System.out.print(y + " ");
		}
		
		for(int z = 0; z < array.length; z++){
			sum = array[z] + sum;
		}
			
		System.out.println(" Die Summe der Komponenten ist " + sum);
		System.out.println(" Der Durchschnitt betr�gt " + ((float)sum/x));
		
	}
}
