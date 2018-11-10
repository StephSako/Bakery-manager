package restaurant;
import java.util.*;

public class Caisse {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String lettre;
		
		Restaurant stockRestaurant = new Restaurant("StudioBagel");
		stockRestaurant.stock.add(new Produit("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new Produit("Burger", 5.75, 10));
		stockRestaurant.stock.add(new Produit("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new Produit("Café", 0.85, 999)); // 999 = illimité
		
		System.out.println("Bienvenue sur l'interface de la vaisse du restaurant Bagel !\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		
		while (!(lettre = sc.next()).equals("q")) {		
			switch (lettre) {					
				
				// On affiche la liste des opérations disponibles
				case "help": 	System.out.println("'h': Afficher cette fenêtre d'aide.\n's' = Ajouter un produit au stock du restaurant" +
				"\n'a' = Ajouter un produit dans une note de client\n'n' = Crér une note d'un client\n'all_stock': Afficher le stock de Bagel\n" + 
				"'p' = Cloturer et faire payer la note d'un client\n'print_note' = Afficher la note d'un client\n'all_notes': Afficher toutes les notes actives\n'q': Quitter le programme"); break;
				
				// On ajoute un produit dans le stock du restaurant
				case "s": 	stockRestaurant.ajouterProduitStockRestaurant(sc);
							break;
				
				// On créé une note de client
				case "n": 	// On créé un client fictif pour l'exercice et on l'ajoute dans la liste des notes de clients actifs
							int idClient;
							System.out.println("Saisissez l'identifiant du client : ");
							while ((idClient = sc.nextInt()) < 0){
								System.out.println("Identifiant client incorrect !\n");
							}
							
							NoteClient note = new NoteClient(idClient);
							
							// On ajoute la nouvelle note dans la liste des notes de clients encore actives
							stockRestaurant.notesClientsActives.add(note);
							
							break;
				
				// Ajouter un produit dans la note d'un client
				case "a": 	// On recherche la note du client si elle existe grâce à son ID ce qui permet d'ouvrir plusieurs notes simultanément				
							NoteClient noteRecovered = stockRestaurant.ouvrirNote(sc);
							if (noteRecovered == null) break;
												
							// On saisie le produit à ajouter dans la note du client récupérée
							noteRecovered.ajouterProduitNoteClient(sc, stockRestaurant);
							break;
							
				// On affiche la note du client
				case "print_note": 	NoteClient noteToPrint = stockRestaurant.ouvrirNote(sc);
							if (noteToPrint == null) break;
							
							// On affiche la note du client
							noteToPrint.afficherNoteAPayer(); break;
				
				// On cloture et fais payer la note au client
				case "p": 	NoteClient noteToFence = stockRestaurant.ouvrirNote(sc);
							if (noteToFence == null) break;
							
							// On affiche la note du client
							noteToFence.cloturerNoteClient(stockRestaurant); break;
				
				// On affiche le stock du restaurant
				case "all_stock": 	stockRestaurant.afficherStock(); break;
				
				// ON affiche toutes les notes actives
				case "all_notes": 	stockRestaurant.afficherNotes(); break;
						
				default: 	System.out.println("Unknow command. Type h for help"); break;
			}
			System.out.println("\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		}
		System.out.println("Vous quittez la caisse enregistreuse ...");
	}
}
