package Aufgabe56bis62;

public class Caipirinha extends Recipe {
	
		
	Caipirinha(){
		Sugar sugar = new Sugar();
		DistilledBeverage cachaca = new DistilledBeverage();
		Fruit limette = new Fruit();
		Miscalellaneous crushedice = new Miscalellaneous();
		
		sugar.setName("Zucker");
		sugar.setWeight(2);
		
		
		addIngredient(sugar, 2);
		addIngredient(cachaca, 6);
		addIngredient(crushedice, 1);
		addIngredient(limette, 1);
	
	
	}
	
	public static void main(String[] args) {
		
		
		
		Caipirinha caipi = new Caipirinha();
		
		DistilledBeverage getr�nk = new DistilledBeverage();
		Fruit getr�nk2 = new Fruit();
		caipi.addIngredient(getr�nk, 1);
		
		System.out.println(caipi.containtsAlcohol());
		
		
//		for(Class i : getr�nk.getClass().getInterfaces()){
//			System.out.println(i);
//		}
//		
//		String[] liste = new String[3];
//		liste[0] = "a";
//		liste[1] = "b";
//		
//		for(String i : liste){
//			System.out.println(i);
//		}
		
//		System.out.println(getr�nk2.getClass().getInterfaces());
//		System.out.println(Alcohol.class);
		
		
		BeachBar akjs= new BeachBar();
		
		
		System.out.println(akjs.containsAlcohol(Coctail.SwimmungPool));
		
		
		
	}

}
