package restaurant;

import java.util.Scanner;
import java.util.InputMismatchException;
import logger.*;

public class Saisie {
	
	public ConsoleLogger logger = new ConsoleLogger();
	
	public Saisie() {}	
	
	public int getSaisieInt(Scanner sc, ConsoleLogger logger, String question, String reponse) {
		int val = 0;		
		while(val <= 0) {
			try {
				logger.info("OUTPUT", question, true);
				val = sc.nextInt();
			} catch (InputMismatchException ex) {
				logger.error("PROGRAM", reponse, true);
				sc.next();
			}
		}
		logger.info("INPUT", "L'utilisateur a tape " + val, false);
		return val;
	}
	
	public double getSaisieDouble(Scanner sc, ConsoleLogger logger, String question, String reponse) {
		double val = 0.0;
		while(val <= 0) {
			try {
				logger.info("OUTPUT", question, true);
				val = sc.nextDouble();
			} catch (InputMismatchException ex) {
				logger.error("PROGRAM", reponse, true);
				sc.next();
			}
		}
		logger.info("INPUT", "L'utilisateur a tape " + val, false);
		return val;
	}
	
	public String getSaisieString(Scanner sc, ConsoleLogger logger) {
		String lettre = sc.next().trim();
		logger.info("INPUT", "L'utilisateur a tape " + lettre, false);
		return lettre.substring(0,1).toUpperCase() + lettre.substring(1).toLowerCase();
	}
}
