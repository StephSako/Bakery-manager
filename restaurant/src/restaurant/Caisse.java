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
		//Saisie saisie = new Saisie();
		
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
		case "b": 	stockRestaurant.ajouterProduitStockRestaurant(); break;
		
		//CREER UN CLIENT
		case "c":	
					String nomClient;
					logger.info("OUTPUT", "Saisissez le nom du client a creer : ");
					nomClient = sc.next();
					nomClient = nomClient.trim();
					
					// On ajoute la nouvelle note dans la liste des notes de clients encore actives
					stockRestaurant.notesClientsActives.add(new NoteClient(nomClient));
					logger.info("PROGRAM", "Le client '" + nomClient + "' a bien ete cree."); break;
		
		//AJOUTER UN PRODUIT A UN CLIENT
		case "d": 	// On recherche la note du client si elle existe grace a son nom ce qui permet d'ouvrir plusieurs notes simultanement				
					NoteClient noteRecovered = stockRestaurant.ouvrirNote();
					if (noteRecovered == null) {
						logger.error("PROGRAM", "Aucune note n'a ete creee a ce nom"); break;
					}
					else {												
						// On saisie le produit a ajouter dans la note du client recuperee
						noteRecovered.ajouterProduitNoteClient(stockRestaurant); break;
					}
					
		//AFFICHER UN CLIENT
		case "e": 	NoteClient noteToPrint;
					do {
						noteToPrint = stockRestaurant.ouvrirNote();
						if (noteToPrint == null) {
							logger.error("PROGRAM", "Aucune note n'a ete creee avec ce nom");
						}
					} while (noteToPrint == null);
		
					// On affiche la note du client
						logger.info("PROGRAM", noteToPrint.afficherNoteAPayer()); break;
					
		//CLOTURER NOTE
		case "f": 
					NoteClient noteToFence;
					do {
						noteToFence = stockRestaurant.ouvrirNote();
						if (noteToFence == null) {
							logger.error("PROGRAM", "Aucune note n'a ete creee avec ce nom");
						}
					} while(noteToFence == null);			
			
					// On affiche la note du client
					noteToFence.cloturerNoteClient(stockRestaurant); break;
		
		//TOUTES LES CLIENTS ACTIFS
		case "g": logger.info("PROGRAM", stockRestaurant.afficherNotes()); break;
		
		//DONNEES COMPTABLES
		case "i": logger.info("PROGRAM", stockRestaurant.donneesComptable()); break;
				
		case "q": 
				logger.info("PROGRAM", "Vous quittez la caisse enregistreuse ...");
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
