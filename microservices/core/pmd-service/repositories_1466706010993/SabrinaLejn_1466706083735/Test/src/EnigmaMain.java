import java.util.Scanner;
public class EnigmaMain {

	public static void main(String[] args) {

		
		Scanner s = new Scanner(System.in);
		
		System.out.print("Walze 1: ");
		int w1 = s.nextInt();
		System.out.print("Walze 2: ");
		int w2 = s.nextInt();
		System.out.print("Walze 3: ");
		int w3 = s.nextInt();
		System.out.print("Schl�ssel: ");
		String key = s.next();
		System.out.print("Zu verschl�sselnder Text: ");
		String Text = s.next();
		
		Enigma e1 = new Enigma(w1, w2, w3, key);
		System.out.println(Text+ " ergibt verschl�sselt: " + e1.encrypt(Text));
		
		System.out.println("________________________________________________________");
		System.out.println("Entschl�sselung");
		System.out.println("");
		
		System.out.print("Walze 1: ");
		w1 = s.nextInt();
		System.out.print("Walze 2: ");
		w2 = s.nextInt();
		System.out.print("Walze 3: ");
		w3 = s.nextInt();
		System.out.print("Schl�ssel: ");
		key = s.next();
		System.out.print("Zu entschl�sselnder Text: ");
		Text = s.next();
		
		Enigma e2 = new Enigma(w1, w2, w3, key);
		System.out.println(Text+ " ergibt entschl�sselt: " + e2.decrypt(Text));
		s.close();
			
	}
	
}

