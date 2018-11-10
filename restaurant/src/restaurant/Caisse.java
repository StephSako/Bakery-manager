package restaurant;
import java.util.*;

public class Caisse {
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		String lettre;
		
		StockRestaurant stockRestaurant = new StockRestaurant("StudioBagel");
		stockRestaurant.stock.add(new Produit("Bagel", 2.5, 20));
		stockRestaurant.stock.add(new Produit("Burger", 5.75, 10));
		stockRestaurant.stock.add(new Produit("Smoothie", 1.25, 30));
		stockRestaurant.stock.add(new Produit("Café", 0.85, 999)); // 999 = illimité
		
		System.out.println("Bienvenue sur l'interface de la vaisse du restaurant Bagel !\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		
		while (!(lettre = sc.next()).equals("q")) {		
			switch (lettre) {					
				
				// On affiche la liste des opérations disponibles
				case "h": 	System.out.println("'h': Afficher cette fenêtre d'aide.\n's' = Ajouter un produit au stock du restaurant\n'c' = Ouvrir une note d'une client\n'l': Afficher le stock de Bagel\n'q': Quitter le programme"); break;
						
				case "s": 	stockRestaurant.ajouterProduitStockRestaurant(sc);
							break;
				
				case "c": 	// On créé un client fictif pour l'exercice
							int idClient = (int) (Math.random()*(10000));
							NoteClient note = new NoteClient(idClient);
							note.menuNoteClient(lettre, sc, stockRestaurant);
							break;
				
				case "l": 	stockRestaurant.afficherStock(); break;
						
				default: 	System.out.println("Unknow command. Type h for help"); break;
			}
			System.out.println("\nQue voulez-vous faire ? ('h' pour afficher l'aide)\n");
		}
		System.out.println("Vous quittez la caisse enregistreuse ...");
	}
}
