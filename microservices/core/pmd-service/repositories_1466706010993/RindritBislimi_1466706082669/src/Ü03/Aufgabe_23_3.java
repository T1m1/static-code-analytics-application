package �03;

import java.util.Scanner;

public class Aufgabe_23_3 {

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);

		int x;
		int z = 0;
		int i = 1;

		System.out.println("Geben Sie eine Obergrenze ein");
		x = s.nextInt();

		do {
			System.out.println("Die Summe betr�gt " + z);
			System.out.println("Der Mittelwert betr�gt " + (z / 2));
			System.out.println("Durchschnitt " + ((float) z / x));

			z = z + i;
			i++;
		}

		while (i <= x);

		s.close();

	}
}
