package restaurant;

import java.util.Scanner;
import java.util.InputMismatchException;
import logger.*;

public class Saisie {
	
	public ConsoleLogger logger = new ConsoleLogger();
	public Scanner sc = new Scanner(System.in);
	
	public Saisie() {}	
	
	public int getSaisieInt(String question, String reponse) {
		int val = 0;		
		while(val <= 0) {
			try {
				this.logger.info("OUTPUT", question, true);
				val = this.sc.nextInt();
			} catch (InputMismatchException ex) {
				this.logger.error("PROGRAM", reponse, true);
				this.sc.next();
			}
		}
		this.logger.info("INPUT", "L'utilisateur a tape " + val, false);
		return val;
	}
	
	public double getSaisieDouble(String question, String reponse) {
		double val = 0.0;
		while(val <= 0) {
			try {
				this.logger.info("OUTPUT", question, true);
				val = this.sc.nextDouble();
			} catch (InputMismatchException ex) {
				this.logger.error("PROGRAM", reponse, true);
				this.sc.next();
			}
		}
		this.logger.info("INPUT", "L'utilisateur a tape " + val, false);
		return val;
	}
	
	public String getSaisieString() {
		String lettre = this.sc.next().trim();
		this.logger.info("INPUT", "L'utilisateur a tape " + lettre, false);
		return lettre.substring(0,1).toUpperCase() + lettre.substring(1).toLowerCase();
	}
}
