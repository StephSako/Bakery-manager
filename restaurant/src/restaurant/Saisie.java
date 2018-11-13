package restaurant;

import java.util.Scanner;
import java.util.InputMismatchException;
import logger.*;

public class Saisie {
	
	public Saisie() {
	}
	
	public int getSaisieInt(Scanner sc, ConsoleLogger logger, String question) {
		int val = 0;		
		while(val <= 0) {
			try {
				logger.info("INPUT", "Saisissez un nombre strictement positif : ");
				val = sc.nextInt();
			} catch (InputMismatchException ex) {
				sc.next();
			}
		}
		return val;
	}
	
	public double getSaisieDouble(Scanner sc, ConsoleLogger logger, String question) {
		double val = 0.0;
		while(val <= 0) {
			try {
				logger.info("INPUT", "Saisissez un nombre a virgule strictement positif : ");
				val = sc.nextDouble();
			} catch (InputMismatchException ex) {
				sc.next();
			}
		}
		return val;
	}
}
