import java.util.Scanner;
public class Aufgabe36 {

	public static void main (String[]args){
	
	Scanner s=new Scanner(System.in);

	
	int []plzArray={78467,78467,78467,78467,78462,78462,78462,78464,78462,78465,78462,78462,78464,78462,78464,78467,78467,78467,78462,78462,78464,78467,78462,78467,78464,78462,78467,78462};
	
	
	String infoArray[]= new String [28];
	
	infoArray[0]= "Holstein´s Backhaus - Markgrafenstr. 17 - 78467 Konstanz - Öffnungszeiten: Mo-Fr: 6.00 - 18.00 Uhr, Sa: 7.00 - 12.30 Uhr";
	infoArray[1]= "Holstein´s Backhaus - Im Kaufland - Zähringerplatz 7 - 78467 Konstanz - Öffnungszeiten: Mo-Fr: 7.00 - 21.00 Uhr, Sa: 7.00 - 21.00 Uhr";
	infoArray[2]= "Holstein´s Backhaus - Carl Benz Str.13 - 78467 Konstanz - Öffnungszeiten: Mo-Fr: 7.00 - 20.00 Uhr, Sa: 8.00 - 20.00 Uhr";
	infoArray[3]= "Holstein´s Backhaus - Moltkestr.3 - 78467 Konstanz - Öffnunfszeiten: Mo-Fr: 6.00 - 18.00 Uhr, Sa: 7.00 - 12.30 Uhr, So: 7.15 - 11.00 Uhr";
	infoArray[4]= "Holstein´s Backhaus - Rosgartenstr. 22 - 78462 Konstanz - Öffnungszeiten: Mo-Fr: 7.00 - 18.30 Uhr, Sa: 7.00 - 17.30 Uhr";
	infoArray[5]= "Holstein´s Backhaus - Hussenstr.21-23 - 78462 Konstanz - Öffnungszeiten: Mo-Fr: 7.00 - 20.00 Uhr, Sa: 7.00 - 18.00 Uhr";		
	infoArray[6]= "Reginbrot - Münzgasse 16 - 78462 Konstanz - Öffnungszeiten: Mo-Fr: 9 - 19 Uhr, Sa: 9 - 18 Uhr";
	infoArray[7]= "Bäckerei Fricke - Mainaustraße 165 - 78464 Konstanz - Öffnungszeiten: Mo-Fr:	6:00-13:00 Uhr, 14:30-18:30 Uhr, Sa: 6:00-12:30 Uhr, So: 8:30-11:00 Uhr";
	infoArray[8]= "Bäckerei Fricke - Sankt-Johann-Gasse 12 - 78462 Konstanz - Öffnungszeiten: Mo-Fr: 6:00-18:00 Uhr, Sa: 7:00-13:00 Uhr";
	infoArray[9]= "Bäckerei Fricke - Martin-Schleyer-Straße 26 - 78465 Konstanz - Öffnungszeiten: Mo,Di,Do,Fr: 6:00-13:00 Uhr 14:30-18:00 Uhr; Sa:	6:00-13:00 Uhr";
	infoArray[10]= "Paradiesbäckerei Menge - Gottlieber Straße 42 - 78462 Konstanz - Öffnungszeiten: Mo.-Fr. 6:00 bis 18:00 Uhr, Sa. 6:00 bis 12:30 Uhr";
	infoArray[11]= "Paradiesbäckerei Menge - Brauneggerstraße 14 - 78462 Konstanz - Öffnungszeiten: Mo.-Fr. 6:30 bis 18:00 Uhr, Sa. 6:30 bis 12:30 Uhr";
	infoArray[12]= "Meisterbäckerei Schneckenburger - Zähringerplatz 22 - 78464 Konstanz - Öffnungszeiten: Mo-Fr: 6:30-19:00 Uhr, Sa-So: 7:00-17:00 Uhr";
	infoArray[13]= "Meisterbäckerei Schneckenburger - Bahnhofplatz 41 - 78462 Konstanz - Öffnungszeiten: Mo-Fr: 5:45-20:00 Uhr, Sa-So: 6:00-18:00 Uhr";
	infoArray[14]= "Meisterbäckerei Schneckenburger - Mainaustraße 79 - 78464 Konstanz - Öffnungszeiten: 7:00-21:00 Uhr, So: 8:00-11:00 Uhr";
	infoArray[15]= "Meisterbäckerei Schneckenburger - Carl-Benz-Straße 22 - 78467 Konstanz - Öffnungszeiten: Mo-Sa: 7:00-22:00 Uhr";
	infoArray[16]= "K&U Bäckerei - Riedstraße 2, 78467 Konstanz - Öffnungszeiten: Mo-Sa: 7:00-20:00 Uhr; So: 8:00-11:00 Uhr";
	infoArray[17]= "K&U Bäckerei - Schneckenburgstraße 5, 78467 Konstanz - Öffnungszeiten: Mo-Sa: 7:00-22:00 Uhr";
	infoArray[18]= "K&U Bäckerei - Gottlieber Str. 34, 78462 Konstanz - Öffnungszeiten: Mo-Sa: 7:00-20:00 Uhr; So: 8:00-11:00 Uhr";
	infoArray[19]= "K&U Bäckerei - Bodanstraße 1, 78462 Konstanz - Öffnungszeiten: Mo-Fr: 7:30-20:00 Uhr, Sa: 8:00-18:00 Uhr, So: 8:00-11:00 Uhr";
	infoArray[20]= "K&U Bäckerei - Staader Str. 2, 78464 Konstanz - Öffnungszeiten: Mo-Mi,Fr-Sa: 8:00-20:00 Uhr, Do: 8:00-22:00 Uhr";
	infoArray[21]= "K&U Bäckerei - Reichenaustraße 202, 78467 Konstanz - Öffnungszeiten: Mo-Do,Sa: 7:00-20:00 Uhr, Fr: 7:00-22:00";
	infoArray[22]= "K&U Bäckerei - Bodanstraße 20-26, 78462 Konstanz - Öffnungszeiten: Mo-Mi,Fr-Sa: 8:00-20:00 Uhr, Do: 8:00-21:00 Uhr";
	infoArray[23]= "Sternbäck - Fürstenbergstr. 91 - 78467 Konstanz";
	infoArray[24]= "Sternbäck - Zähringerplatz 9 - 78464 Konstanz - Öffnungszeiten: Mo-Sa: 8:00-19:00 Uhr";
	infoArray[25]= "Sternbäck - Bodanstr. 1-3 - 78462 Konstanz";
	infoArray[26]= "Sternbäck - Max-Stromeyerstr. 55 78467 Konstanz";
	infoArray[27]= "Sternbäck - Bodanstr. 1-3 - 78462 Konstanz";
	
	//System.out.println(infoArray[15]);
			
	int gesucht;

do{	
	System.out.println("Please enter a zip code of Konstanz: ");
	gesucht=s.nextInt();}
	while(gesucht<78462|gesucht>78467);

	for(int i=0; i < plzArray.length; i++){
	    if(plzArray[i]==gesucht){
	         System.out.println(infoArray[i]);
	}
}
	s.close();
}}