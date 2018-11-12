package restaurant;

import java.util.Scanner;
import java.util.InputMismatchException;
import logger.*;

public class Saisie {
	
	public Saisie() {
	}
	
	public int getSaisieInt(Scanner sc, ConsoleLogger logger, String question) {
		int maValeur = 0;
		boolean test = false;
		
		do {
		logger.info("OUTPUT", question);
		test = true;
		try {
			maValeur = sc.nextInt();
		} catch(InputMismatchException ex) {
			logger.error("OUTPUT", "Saisissez un nombre entier");
			test = false;
		}
		} while(!test);
		return maValeur;
	}
}
