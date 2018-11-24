package restaurant;
import java.util.*;

import logger.*;

public class Caisse {
	
	public Scanner sc = new Scanner(System.in);
	public ConsoleLogger logger = new ConsoleLogger();
	public String lettre;
	public Operation operation = new Operation();
	// Creation du restaurant
	Restaurant stockRestaurant = new Restaurant("StudioBagel");
	
	private final Map<String, Runnable> commands = new HashMap<>();
	
	public Caisse() {
		commands.put("a", () -> operation.casA(logger, stockRestaurant));
		commands.put("b", () -> operation.casB(logger, stockRestaurant));
		commands.put("c", () -> operation.casC(stockRestaurant));
		commands.put("d", () -> operation.casD(logger, stockRestaurant));
		commands.put("e", () -> operation.casE(logger, stockRestaurant));
		commands.put("f", () -> operation.casF(logger, stockRestaurant));
		commands.put("g", () -> operation.casG(logger, stockRestaurant));
		commands.put("h", () -> operation.casH(logger));
		commands.put("i", () -> operation.casI(logger, stockRestaurant));
		commands.put("q", () -> operation.casQ(logger));
	}
	
	public Restaurant debutDeJournee() {	
		// Remplissage du restaurant
		stockRestaurant.stock.add(new ProduitStockFinis("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new ProduitStockFinis("Burger", 5.75, 10));
		stockRestaurant.stock.add(new ProduitStockFinis("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new ProduitStockInfinis("Cafe", 0.5)); // Pas de paramètres stock car le stock est illimité
		logger.info("OUTPUT", "-------------------------------------------------------------\nBienvenue sur l'interface de la caisse du restaurant Bagel !\n-------------------------------------------------------------\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n", true);
		return stockRestaurant;
	}
	
	public void journee(Restaurant stockRestaurant) {
		lettre = (sc.next()).trim();
		if (commands.get(lettre) != null) commands.get(lettre).run();
		else logger.error("PROGRAM", "Commande inconnue. Tappez 'help' pour l'aide", true);
		logger.info("OUTPUT", "Que voulez-vous faire ? ('h' pour afficher l'aide)\n", true);
	}	
	public void finDeJournee() {
		stockRestaurant = debutDeJournee();
		do {
			journee(stockRestaurant);
		} while (!(lettre).equals("q"));
	}
}