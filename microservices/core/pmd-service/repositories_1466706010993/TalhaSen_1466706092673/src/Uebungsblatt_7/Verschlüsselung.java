package Uebungsblatt_7;

	import java.util.Scanner;
	 
	public class Verschl�sselung {
	    public static void main (String[]args){
	        Scanner scanner = new Scanner(System.in);
	         
	        System.out.print("Geben Sie die erste Walzennummer ein:\n");
	        int walze1 = scanner.nextInt();
	        System.out.print("Geben Sie die zweite Walzennummer ein:\n");
	        int walze2 = scanner.nextInt();
	        System.out.print("Geben Sie die zweite Walzennummer ein:\n");
	        int walze3 = scanner.nextInt();
	        System.out.print("Geben Sie eine Schl�ssel ein:\n");
	        String schl�ssel= scanner.next().toUpperCase();
	     
	         
	        Enigma enigma = new Enigma(walze1,walze2,walze3,schl�ssel);
	         
	        System.out.print("M�chten Sie Verschl�sseln oder Entschl�sseln?"
	                + " W�hlen Sie f�r das verschl�sseln die 1 und f�r das entschl�sseln die 2"
	                + "\n 1. Verschl�sseln \n 2. Entschl�sslen\n");
	        int x = scanner.nextInt();
	         
	        switch(x){
	        case 1: 
	            System.out.print("Geben Sie einen Text zur Verschl�sselung ein:\n");
	            String text = scanner.next().toUpperCase();
	            System.out.print("Verschl�sselter Text:");
	            System.out.println(enigma.encrypt(text));
	               break;
	        case 2:
	            System.out.print("Geben Sie einen Text zur Entschl�sselung ein:\n");
	            String text2 = scanner.next().toUpperCase();
	            System.out.println("Entschl�sselter Text:");
	            System.out.println(enigma.decrypt(text2));
	               break;
	        default: System.out.println("Falsche Eingabe");
	        }
	         
	         
	         
	        scanner.close();
	    }
	}

