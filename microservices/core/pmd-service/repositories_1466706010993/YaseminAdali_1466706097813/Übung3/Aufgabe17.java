import java.util.Scanner;

public class Aufgabe17 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		double Gewicht;
		double Gr��e;
		double Berechnung;
		double Gr��e1;
		
		Scanner Scanner = new Scanner (System.in);
		
		System.out.println("Geben Sie Ihr Gewicht ein:");
		
		Gewicht=Scanner.nextDouble();
		
		System.out.println("Geben Sie Ihre Gr��e ein:");
		
		Gr��e=Scanner.nextDouble();
		
		Gr��e1=Gr��e*Gr��e;
		
		Berechnung=Gewicht/Gr��e1;
		
		System.out.println("Ihr BMI betr�gt:   "+Berechnung);
		
		if (Berechnung<18.5){
		System.out.println("Sie sind Untergewichtig");
		}
		if(Berechnung>=18.5 && Berechnung<25){
		System.out.println("Sie sind Normalgewichtig");
		}
		if (Berechnung>=25 && Berechnung<30){
		System.out.println("�bergewichtig");
		}
		if(Berechnung>=30 && Berechnung>40){
			System.out.println("Starkes �bergewicht");	
		}
		if (Berechnung>=40){
			System.out.println("Extreme Adiposita");
		}
	}
}
