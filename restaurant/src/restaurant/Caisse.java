package restaurant;
import java.util.*;
import logger.*;

public class Caisse {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in); String lettre;
		
		// Cr�ation du restaurant
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant.stock.add(new Produit("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new Produit("Burger", 5.75, 10));
		stockRestaurant.stock.add(new Produit("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new Produit("Caf�", 0.85, 999)); // 999 = illimité
		
		// Création du logger
		ConsoleLogger logger = new ConsoleLogger();		
		// Création du LogFileWriter
		LogFileWriter lfw = new LogFileWriter();
		lfw.ecrireFinLogFile("OUTPUT", "INFO", "Initialisation ...");
		
		logger.info("OUTPUT", "Bienvenue sur l'interface de la caisse du restaurant Bagel !\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		
		while (!(lettre = sc.next()).equals("q")) {		
			switch (lettre) {					
				
				// On affiche la liste des op�rations disponibles
				case "h": logger.info("OUTPUT", "'h': Afficher cette fen�tre d'aide.\n'a': Afficher le stock de Bagel\n'b' : Ajouter un produit au stock du restaurant" +
				"\n'c' : Cr�er une note d'un client\n'd' : Ajouter un produit dans une note de client\n'e' : Afficher la note d'un client\n" + 
				"'f' : Cl�turer et faire payer la note d'un client\n'g': Afficher toutes les notes actives\n'i': Afficher les donn�es comptables\n'q': Quitter le programme"); break;
				
				// On affiche le stock du restaurant
				case "a": 	logger.info("OUTPUT", stockRestaurant.afficherStock()); break;
				
				// On ajoute un produit dans le stock du restaurant
				case "b": 	stockRestaurant.ajouterProduitStockRestaurant(sc); break;
				
				// On cr�e une note de client
				case "c":					
							int idClient;
							logger.info("OUTPUT", "Saisissez l'identifiant du client : ");
							while ((idClient = sc.nextInt()) < 0){
								logger.error("OUTPUT", "Identifiant client incorrect !\n");
							}
							
							// On ajoute la nouvelle note dans la liste des notes de clients encore actives
							stockRestaurant.notesClientsActives.add(new NoteClient(idClient));
							logger.info("OUTPUT", "Le client '" + idClient + "' a bien �t� cr��."); break;
				
				// Ajouter un produit dans la note d'un client
				case "d": 	// On recherche la note du client si elle existe gr�ce � son ID ce qui permet d'ouvrir plusieurs notes simultan�ment				
							NoteClient noteRecovered = stockRestaurant.ouvrirNote(sc, logger);
							if (noteRecovered == null) {
								logger.error("OUTPUT", "Aucune note n'a �t� cr��e avec cet identifiant"); break;
							}
												
							// On saisie le produit � ajouter dans la note du client r�cup�r�e
							noteRecovered.ajouterProduitNoteClient(sc, stockRestaurant, logger); break;
							
				// On affiche la note du client
				case "e": 	NoteClient noteToPrint = stockRestaurant.ouvrirNote(sc, logger);
							if (noteToPrint == null) break;
							
							// On affiche la note du client
							logger.info("OUTPUT", noteToPrint.afficherNoteAPayer()); break;
				
				// On cloture et fais payer la note au client
				case "f": 
					NoteClient noteToFence = stockRestaurant.ouvrirNote(sc, logger);
					if (noteToFence == null) break;
					
					// On affiche la note du client
					noteToFence.cloturerNoteClient(stockRestaurant); break;
				
				// On affiche toutes les notes actives
				case "g": logger.info("OUTPUT", stockRestaurant.afficherNotes()); break;
				
				case "i": logger.info("OUTPUT", stockRestaurant.donneesComptable()); break;
						
				default: logger.error("OUTPUT", "Commande inconnue. Tappez 'help' pour l'aide"); break;
			}
			logger.info("OUTPUT", "\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		}
		logger.info("OUTPUT", "Vous quittez la caisse enregistreuse ...");
	}
}
