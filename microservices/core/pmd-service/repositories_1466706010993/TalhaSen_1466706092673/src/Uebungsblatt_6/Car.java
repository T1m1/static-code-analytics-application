package Uebungsblatt_6;

public class Car {

	public String colour; // Attribute f�r Farbe 
	
	public int MaxSpeed; // Attribute f�r Maximalgeschwindigkeit
	
	public String Manufacturer; // Attribute f�r Hersteller	
	String Description(){ //Instanzmethode Description mit dem R�ckabewert String
		String car="Dieses Auto der Marke " +this.Manufacturer+" in " +this.colour+ " f�hrt bis zu " +this.MaxSpeed+ " km/h schnell";
		return car;
	}
	
}
