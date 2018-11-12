package restaurant;
import java.util.*;
import logger.*;

public class Caisse {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in); String lettre;
		
		// CrÃ©ation du restaurant
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant.stock.add(new Produit("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new Produit("Burger", 5.75, 10));
		stockRestaurant.stock.add(new Produit("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new Produit("Café", 0.85, 999)); // 999 = illimitÃ©
		
		// CrÃ©ation du logger
		ConsoleLogger logger = new ConsoleLogger();		
		// CrÃ©ation du LogFileWriter
		LogFileWriter lfw = new LogFileWriter();
		lfw.ecrireFinLogFile("OUTPUT", "INFO", "Initialisation ...");
		
		logger.info("OUTPUT", "Bienvenue sur l'interface de la caisse du restaurant Bagel !\nQue voulez-vous faire ? ('help' pour afficher l'aide)\n");
		
		while (!(lettre = sc.next()).equals("q")) {		
			switch (lettre) {					
				
				// On affiche la liste des opÃ©rations disponibles
				case "help": logger.info("OUTPUT", "'help': Afficher cette fenêtre d'aide.\n'add_stock' = Ajouter un produit au stock du restaurant" +
				"\n'add_note' = Ajouter un produit dans une note de client\n'create_note' = Créer une note d'un client\n'all_stock': Afficher le stock de Bagel\n" + 
				"'pay_note' = Clôturer et faire payer la note d'un client\n'print_note' = Afficher la note d'un client\n'all_notes': Afficher toutes les notes actives\n'q': Quitter le programme"); break;
				
				// On ajoute un produit dans le stock du restaurant
				case "add_stock": 	stockRestaurant.ajouterProduitStockRestaurant(sc); break;
				
				// On crée une note de client
				case "create_note":					
							int idClient;
							logger.info("OUTPUT", "Saisissez l'identifiant du client : ");
							while ((idClient = sc.nextInt()) < 0){
								logger.error("OUTPUT", "Identifiant client incorrect !\n");
							}
							
							// On ajoute la nouvelle note dans la liste des notes de clients encore actives
							stockRestaurant.notesClientsActives.add(new NoteClient(idClient));
							logger.info("OUTPUT", "Le client '" + idClient + "' a bien été créé."); break;
				
				// Ajouter un produit dans la note d'un client
				case "add_note": 	// On recherche la note du client si elle existe grâce à  son ID ce qui permet d'ouvrir plusieurs notes simultanément				
							NoteClient noteRecovered = stockRestaurant.ouvrirNote(sc, logger);
							if (noteRecovered == null) {
								logger.error("OUTPUT", "Aucune note n'a été créée avec cet identifiant"); break;
							}
												
							// On saisie le produit à  ajouter dans la note du client récupérée
							noteRecovered.ajouterProduitNoteClient(sc, stockRestaurant, logger); break;
							
				// On affiche la note du client
				case "print_note": 	NoteClient noteToPrint = stockRestaurant.ouvrirNote(sc, logger);
							if (noteToPrint == null) break;
							
							// On affiche la note du client
							logger.info("OUTPUT", noteToPrint.afficherNoteAPayer()); break;
				
				// On cloture et fais payer la note au client
				case "pay_note": 
					logger.info("OUTPUT", "Saisissez l'identifiant du client : ");
					NoteClient noteToFence = stockRestaurant.ouvrirNote(sc, logger);
					if (noteToFence == null) break;
					
					// On affiche la note du client
					noteToFence.cloturerNoteClient(stockRestaurant); break;
				
				// On affiche le stock du restaurant
				case "all_stock": 	logger.info("OUTPUT", stockRestaurant.afficherStock()); break;
				
				// On affiche toutes les notes actives
				case "all_notes": 	logger.info("OUTPUT", stockRestaurant.afficherNotes()); break;
						
				default: logger.error("OUTPUT", "Commande inconnue. Tappez 'help' pour l'aide"); break;
			}
			logger.info("OUTPUT", "\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		}
		logger.info("OUTPUT", "Vous quittez la caisse enregistreuse ...");
	}
}
