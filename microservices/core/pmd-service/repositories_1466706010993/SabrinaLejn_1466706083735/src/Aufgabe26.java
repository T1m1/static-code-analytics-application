import java.util.Arrays;
import java.util.Scanner;

public class Aufgabe26 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int enNumber;

		int[] anIntArray = null;
		System.out.print("Please enter a number: ");
		enNumber = scanner.nextInt();

		anIntArray = new int[enNumber];
		Arrays.sort(anIntArray);
		for (int id = 0; id < anIntArray.length; id++) {
			anIntArray[id] = (int) (Math.random() * 201) - 100;
			System.out.println(id + "=" + anIntArray[id]);
		}

		scanner.close();
	}
}