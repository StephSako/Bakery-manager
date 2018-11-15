package restaurant;
import java.util.*;
import logger.*;

public class Caisse {
	
	public Scanner sc = new Scanner(System.in);
	public ConsoleLogger logger = new ConsoleLogger();
	public String lettre;
	
	public Caisse() {
	}
	
	public Restaurant debutDeJournee() {	
		// Creation du restaurant
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant.stock.add(new Produit("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new Produit("Burger", 5.75, 10));
		stockRestaurant.stock.add(new Produit("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new Produit("Cafe", 0.85, 999)); // 999 = illimité
		
		
		// Creation du LogFileWriter
		LogFileWriter lfw = new LogFileWriter();
		lfw.ecrireFinLogFile("OUTPUT", "INFO", "Initialisation ...");
		
		logger.info("OUTPUT", "-------------------------------------------------------------\nBienvenue sur l'interface de la caisse du restaurant Bagel !\n-------------------------------------------------------------\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		
		return stockRestaurant;
	}
	
	public void journee(Restaurant stockRestaurant) {
		
		lettre = sc.next();
		lettre = lettre.trim();
		switch (lettre) {					
		
		//OPERATIONS DISPONIBLES
		case "h": logger.info("PROGRAM", "'h' : Afficher cette fenetre d'aide.\n'a' : Afficher le stock de Bagel\n'b' : Ajouter un produit au stock du restaurant" +
		"\n'c' : Creer une note d'un client\n'd' : Ajouter un produit dans une note de client\n'e' : Afficher la note d'un client\n" + 
		"'f' : Cloturer et faire payer la note d'un client\n'g' : Afficher toutes les notes actives\n'i' : Afficher les donnees comptables\n'q' : Quitter le programme"); break;
		
		//AFFICHER STOCK DU RESTAURANT
		case "a": 	logger.info("PROGRAM", stockRestaurant.afficherStock()); break;
		
		//AJOUTER UN PRODUIT AU STOCK
		case "b": 	
					logger.info("OUTPUT", "Nom du produit a ajouter :");
					stockRestaurant.ajouterProduitStockRestaurant(); break;
		
		//CREER UN CLIENT
		case "c":	stockRestaurant.existenceClientEtAjout(); break;
					
		//AJOUTER UN PRODUIT A UN CLIENT
		case "d": 	// On recherche la note du client si elle existe grace a son nom ce qui permet d'ouvrir plusieurs notes simultanement				
					logger.info("OUTPUT", "Saisissez le nom du client : ");
					NoteClient noteRecovered = stockRestaurant.ouvrirNote();
					if (noteRecovered == null) {
						logger.error("PROGRAM", "Aucune note n'a ete creee a ce nom"); break;
					}
					else {												
						// On saisie le produit a ajouter dans la note du client recuperee
						logger.info("OUTPUT", "Saisir le produit a ajouter parmi : ");
						noteRecovered.ajouterProduitNoteClient(stockRestaurant); break;
					}
					
		//AFFICHER UN CLIENT
		case "e": 	logger.info("OUTPUT", "Saisissez le nom du client : ");
					NoteClient noteToPrint = stockRestaurant.ouvrirNote();
					if (noteToPrint == null) {
						logger.error("PROGRAM", "Aucune note n'a ete creee avec ce nom"); break;
					} else {
						// On affiche la note du client
						logger.info("PROGRAM", noteToPrint.afficherNoteAPayer()); break;
					}
					
		//CLOTURER NOTE
		case "f": 
					logger.info("OUTPUT", "Saisissez le nom du client : ");
					NoteClient noteToFence = stockRestaurant.ouvrirNote();
					if (noteToFence == null) {
						logger.error("PROGRAM", "Aucune note n'a ete creee avec ce nom"); break;
					} else {
						// On affiche la note du client
						noteToFence.cloturerNoteClient(stockRestaurant);
						logger.info("PROGRAM", "Merci ! La note a bien ete encaissee."); break;
					}
					
		//TOUTES LES CLIENTS ACTIFS
		case "g": logger.info("PROGRAM", stockRestaurant.afficherNotes()); break;
		
		//DONNEES COMPTABLES
		case "i": logger.info("PROGRAM", stockRestaurant.donneesComptable()); break;
				
		case "q": 
				logger.info("PROGRAM", "Vous quittez la caisse enregistreuse ...");
				System.exit(1);
				//faire quitter la caisse enregistreuse
				
		default: logger.error("PROGRAM", "Commande inconnue. Tappez 'help' pour l'aide"); break;
		}
		logger.info("OUTPUT", "\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
	}
	
	public void finDeJournee() {
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant = debutDeJournee();
		do {
			journee(stockRestaurant);
		} while (!(lettre).equals("q"));
	}
}
