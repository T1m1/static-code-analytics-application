
import java.util.Scanner; //scanner muss klasse aktivieren
public class Variablen {

	
public static void main(String[] args) {

	
Scanner f�rName = new Scanner(System.in); //scanner f�r namensgebung
	
	
byte winAnzahl = 35;
short studierendeHTWG = 5000;
float einwohnerEU = 503000000;  // verwendung von float weil f�r Aufgabe 6 relevant.
long weltbev�lkerung = 7284283000L;
byte anzahlSemster = 8;
char buchstabeNachname = 'g';
float noteKlausur = (float) 2.9;
boolean schaltjahr = true;
String vorname;
String nachname;


System.out.println("Hallo Sebastian");




System.out.println("eingabe von vorname");
vorname = f�rName.nextLine();
System.out.println("eingabe von nachname");
nachname = f�rName.nextLine();
System.out.println(vorname +" "+ nachname);

System.out.println(nachname +" "+vorname);



System.out.println((einwohnerEU*100)/weltbev�lkerung + "%");




;

}
}
