package �bungsblatt6;

public class Car {

	static String Colour;
	static int MaxSpeed;
	static String Manufacturer;

	// public Car(String Colour, int MaxSpeed, String Manufacturer){
	//
	// this.Colour = Colour;
	// this.MaxSpeed = MaxSpeed;
	// this.Manufacturer = Manufacturer;
	// }
	
	public Car(){ 			//Default-Konstruktor
		Colour = "silber";	//-->mehrere Konstruktoren in einer Klasse 
		MaxSpeed = 150;		// == �berladene Konstruktoren
		Manufacturer = "Volkswagen";
	}

	public String Description() {

	//	this.Colour = Colour; 			// this.Instanzvariablennname = Parametername
	//this.MaxSpeed = MaxSpeed;
	//	this.Manufacturer = Manufacturer;

		return "Dieses " + this.Manufacturer + "-Auto in " + this.Colour + " f�hrt bis zu " + this.MaxSpeed
				+ " km/h schnell";

	}

}
