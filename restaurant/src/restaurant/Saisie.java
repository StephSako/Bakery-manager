package restaurant;

import java.util.Scanner;
import java.util.InputMismatchException;
import logger.*;

public class Saisie {
	
	// CONSTRUCTEUR
	
	public Saisie() {}
	
	
	// FONCTIONS
	
	public int getSaisieInt(Scanner sc, ConsoleLogger logger, String question, String reponse) {
		int val = 0;		
		while(val <= 0) {
			try {
				logger.info("OUTPUT", question);
				val = sc.nextInt();
			} catch (InputMismatchException ex) {
				logger.error("PROGRAM", reponse);
				sc.next();
			}
		}
		return val;
	}
	
	public double getSaisieDouble(Scanner sc, ConsoleLogger logger, String question, String reponse) {
		double val = 0.0;
		while(val <= 0) {
			try {
				logger.info("OUTPUT", question);
				val = sc.nextDouble();
			} catch (InputMismatchException ex) {
				logger.error("PROGRAM", reponse);
				sc.next();
			}
		}
		return val;
	}
}
