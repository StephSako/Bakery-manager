package restaurant;
import java.util.*;

public class Caisse {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String lettre;
		
		// Création du restaurant
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant.stock.add(new Produit("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new Produit("Burger", 5.75, 10));
		stockRestaurant.stock.add(new Produit("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new Produit("Café", 0.85, 999)); // 999 = illimité
		
		// Création du logger
		ConsoleLogger logger = new ConsoleLogger();
		
		logger.print("Bienvenue sur l'interface de la vaisse du restaurant Bagel !\nQue voulez-vous faire ? ('help' pour afficher l'aide)\n");
		
		while (!(lettre = sc.next()).equals("q")) {		
			switch (lettre) {					
				
				// On affiche la liste des opérations disponibles
				case "help": logger.print("'help': Afficher cette fenêtre d'aide.\n'add_stock' = Ajouter un produit au stock du restaurant" +
				"\n'add_note' = Ajouter un produit dans une note de client\n'create_note' = Créer une note d'un client\n'all_stock': Afficher le stock de Bagel\n" + 
				"'pay_note' = Cloturer et faire payer la note d'un client\n'print_note' = Afficher la note d'un client\n'all_notes': Afficher toutes les notes actives\n'q': Quitter le programme"); break;
				
				// On ajoute un produit dans le stock du restaurant
				case "add_stock": 	stockRestaurant.ajouterProduitStockRestaurant(sc);
							break;
				
				// On créé une note de client
				case "create_note": 	// On créé un client fictif pour l'exercice et on l'ajoute dans la liste des notes de clients actifs
							int idClient;
							System.out.println("Saisissez l'identifiant du client : ");
							while ((idClient = sc.nextInt()) < 0){
								logger.print("Identifiant client incorrect !\n");
							}
							
							NoteClient note = new NoteClient(idClient);
							
							// On ajoute la nouvelle note dans la liste des notes de clients encore actives
							stockRestaurant.notesClientsActives.add(note);
							
							break;
				
				// Ajouter un produit dans la note d'un client
				case "add_note": 	// On recherche la note du client si elle existe grâce à son ID ce qui permet d'ouvrir plusieurs notes simultanément				
							NoteClient noteRecovered = stockRestaurant.ouvrirNote(sc, logger);
							if (noteRecovered == null) {
								logger.print("Aucune note n'a été créée avec cet identifiant"); break;
							}
												
							// On saisie le produit à ajouter dans la note du client récupérée
							noteRecovered.ajouterProduitNoteClient(sc, stockRestaurant, logger);
							break;
							
				// On affiche la note du client
				case "print_note": 	NoteClient noteToPrint = stockRestaurant.ouvrirNote(sc, logger);
							if (noteToPrint == null) break;
							
							// On affiche la note du client
							logger.print(noteToPrint.afficherNoteAPayer()); break;
				
				// On cloture et fais payer la note au client
				case "pay_note": 
					logger.print("Saisissez l'identifiant du client : ");
					NoteClient noteToFence = stockRestaurant.ouvrirNote(sc, logger);
					if (noteToFence == null) break;
					
					// On affiche la note du client
					noteToFence.cloturerNoteClient(stockRestaurant); break;
				
				// On affiche le stock du restaurant
				case "all_stock": 	logger.print(stockRestaurant.afficherStock()); break;
				
				// On affiche toutes les notes actives
				case "all_notes": 	logger.print(stockRestaurant.afficherNotes()); break;
						
				default: logger.print("Commande inconnue. Tappez 'help' pour l'aide"); break;
			}
			logger.print("\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		}
		logger.print("Vous quittez la caisse enregistreuse ...");
	}
}
