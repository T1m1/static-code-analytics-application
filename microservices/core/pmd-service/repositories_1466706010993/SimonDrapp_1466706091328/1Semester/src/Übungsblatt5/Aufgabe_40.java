package �bungsblatt5;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Aufgabe_40 {

	static char currentletter;
	static char letter;
	static int count;

	static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("Bitte geben Sie einen Buchstaben ein: ");
		letter = s.next().charAt(0); // erstes Zeichen der Eingabe wird ausgew�hlt

		leseDatei();

		System.out.println("Die Anzahl von dem Buchstaben " + letter + " betr�gt: " + count);

	}

	public static void leseDatei() {

		try {
			
			File faust = new File("C:\\Users\\simon\\Desktop\\Programmieren\\faust.txt");
//			File faust = new File("C:\\Users\\simon\\Desktop\\Programmieren\\Alphabet.txt");

			FileReader f = new FileReader(faust); // Anschlussstrom f�r Zeichen, der sich
												  // mit einer Textdatei verbindet

			BufferedReader b = new BufferedReader(f); // kehrt erst dann wieder zum Lesen der Datei zur�ck,
													  // wennd der Puffer leer ist (weil das Programm alles daraus gelesen hat)
			
			String zeile = null; // String-Variable die jeweils die Zeile aufnimmt, w�hrend sie gelesen wird

			while ((zeile = b.readLine()) != null) {	// Variable wird erste Zeile �bergeben und darf nicht null sein
				for (int i = 0; i < zeile.length(); i++) { // alle Zeichen werden �berpr�ft

					currentletter = zeile.charAt(i); //Variable wird jeweils dem entsprechenden Zeichen �bergeben

					if (currentletter == letter) { //nach Zeichen �berpr�fen
						count++;
					}
				}
			}
			b.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
}
