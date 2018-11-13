package restaurant;
import java.util.*;
import logger.*;

public class Caisse {
	
	public Scanner sc = new Scanner(System.in);
	public String lettre;
	public ConsoleLogger logger = new ConsoleLogger();
	
	public Caisse() {
		this.lettre = sc.next();
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
		
		logger.info("OUTPUT", "Bienvenue sur l'interface de la caisse du restaurant Bagel !\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		
		return stockRestaurant;
	}
	
	public void journee() {
		
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant = debutDeJournee();		
		
		switch (lettre) {					
		
		//OPERATIONS DISPONIBLES
		case "h": logger.info("PROGRAM", "'h' : Afficher cette fenetre d'aide.\n'a' : Afficher le stock de Bagel\n'b' : Ajouter un produit au stock du restaurant" +
		"\n'c' : Creer une note d'un client\n'd' : Ajouter un produit dans une note de client\n'e' : Afficher la note d'un client\n" + 
		"'f' : Cloturer et faire payer la note d'un client\n'g' : Afficher toutes les notes actives\n'i' : Afficher les donnees comptables\n'q' : Quitter le programme"); break;
		
		//AFFICHER STOCK DU RESTAURANT
		case "a": 	logger.info("PROGRAM", stockRestaurant.afficherStock()); break;
		
		//AJOUTER UN PRODUIT AU STOCK
		case "b": 	stockRestaurant.ajouterProduitStockRestaurant(sc, logger); break;
		
		//CREER UN CLIENT
		case "c":	
					String nomClient;
					logger.info("OUTPUT", "Saisissez l'identifiant du client a creer : ");
					nomClient = sc.next();
					nomClient = nomClient.trim();
					
					// On ajoute la nouvelle note dans la liste des notes de clients encore actives
					stockRestaurant.notesClientsActives.add(new NoteClient(nomClient));
					logger.info("PROGRAM", "Le client '" + nomClient + "' a bien ete cree."); break;
		
		//AJOUTER UN PRODUIT A UN CLIENT
		case "d": 	// On recherche la note du client si elle existe grace a son ID ce qui permet d'ouvrir plusieurs notes simultan�ment				
					NoteClient noteRecovered = stockRestaurant.ouvrirNote(sc, logger);
					if (noteRecovered == null) {
						logger.error("PROGRAM", "Aucune note n'a ete creee avec cet identifiant"); break;
					}
					else {												
						// On saisie le produit a ajouter dans la note du client recuperee
						noteRecovered.ajouterProduitNoteClient(sc, stockRestaurant, logger); break;
					}
					
		//AFFICHER UN CLIENT
		case "e": 	NoteClient noteToPrint = stockRestaurant.ouvrirNote(sc, logger);
					if (noteToPrint == null) break;
					
					// On affiche la note du client
					logger.info("PROGRAM", noteToPrint.afficherNoteAPayer()); break;
		
		//CLOTURER NOTE
		case "f": 
					NoteClient noteToFence = stockRestaurant.ouvrirNote(sc, logger);
					if (noteToFence == null) break;
			
					// On affiche la note du client
					noteToFence.cloturerNoteClient(stockRestaurant, logger); break;
		
		//TOUTES LES CLIENTS ACTIFS
		case "g": logger.info("PROGRAM", stockRestaurant.afficherNotes()); break;
		
		//DONNEES COMPTABLES
		case "i": logger.info("PROGRAM", stockRestaurant.donneesComptable()); break;
				
		default: logger.error("PROGRAM", "Commande inconnue. Tappez 'help' pour l'aide"); break;
		}
		logger.info("OUTPUT", "\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
	}
	
	public void finDeJournee() {
		while (!(lettre).equals("q")) {		
			journee();
		}
		sc.close();
		logger.info("PROGRAM", "Vous quittez la caisse enregistreuse ...");
	}
}
