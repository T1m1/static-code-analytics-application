
import java.util.Scanner;

public class B�ckerei {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		System.out.println("Geben Sie die PLZ ein:  ");
		System.out.println("z.B. 78467,78464,78462 ,78465");
		String plz = input.nextLine();
		String[] split = plz.split("");
		if (split.length == 5) {
			if ((split[0] == "7") && (split[1] == "8") && (split[2] == "4")) {
				switch (plz) {
				case "78467":
					System.out.println("Sternenb�ckerei : F�rstenbergstr. 91 ");
					System.out.println("Paradiesb�ckerei : Max-Stromeyerstr. 55 ");
					System.out.println("Meisterb�ckerei : Carl-Benz-Stra�e 18-22");
					System.out.println("Holsteins-Backhaus : Moltkestr.3 ");
					System.out.println("Holsteins-Backhaus : Holstein's Backhaus GmbH  Carl Benz Str.13");
					System.out.println("Holsteins-Backhaus : Holstein's Backhaus Im Kaufland Z�hringerplatz 7 ");
					break;
				case "78464":
					System.out.println("Meisterb�ckerei : Z�hringerplatz 9 ");
					System.out.println("Meisterb�ckerei : Kanzleistra�e 10 ");
					break;
				case "78462":
					System.out.println("Bodanstr. 1-3 ");
					System.out.println("Paradiesb�ckerei : Gottlieber Stra�e 42 Konstanz");
					System.out.println("Paradiesb�ckerei : Brauneggerstra�e 14");
					System.out.println("Reginbrot : M�nzgasse 16 Konstanz");
					System.out.println("Holsteins-B�ckerei : Holsteins Backhaus Hussenstr.21-23 ");
					System.out.println("Holsteins-B�ckerei : Rosgartenstr. 22");
					break;
				case "78465":
					System.out.println("B�ckerei Fricke : Martin-Schleyer-Stra�e 26");
					break;
				default:
					System.out.println("Die angegebene PLZ ist nicht g�ltig! \n Bitte neue PLZ angeben:");

				}
			}

		} else {
			System.out.println("eine PLZ hat die l�nge 5");
		}
	}

}