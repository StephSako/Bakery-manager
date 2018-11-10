package restaurant;
import java.util.LinkedList;
import java.util.Scanner;

public class NoteClient {
	
	public LinkedList<Produit> panier = new LinkedList<Produit>();
	public int idClient;
	
	public NoteClient(int idClient) {
		this.idClient = idClient;
	}
	
	public void ajouterProduitNoteClient(Scanner sc, StockRestaurant SR){
		String nom = "";
		double prix = 0;
		int stock;
		
		System.out.println("Saisir un nom : ");
		while ((nom = sc.next()).equals("")){
			System.out.println("Nom incorrect !\n");
		}
		
		int i = 0;
		while(i < SR.stock.size()){
			if (SR.stock.get(i).nom == nom) {
				prix = SR.stock.get(i).prix;
				break;
			}
			i++;
		}
		
		System.out.println("Nombre de " + nom + " à ajouter au panier : ");
		while ((stock = sc.nextInt()) <= 0){
			System.out.println("Stock incorrect !\n");
		}
		
		// On ajoute le produit saisie au panier du client
		this.panier.add(new Produit(nom, prix, stock));
		
		// On retire le produit au stok du restaurant
		int j = 0;
		while(j < SR.stock.size()){
			if (SR.stock.get(j).nom == nom) {
				SR.stock.get(j).stock -= stock;
				break;
			}
			j++;
		}
	}
	
	public void afficherNoteAPayer() {
		// Calcul du prix total HT
		double prixTotalHT = 0;
		for (Produit produit : panier) {
			prixTotalHT = prixTotalHT + produit.prix;
		}
		
		// Calcul du prix total HT
		double prixTotalTTC = prixTotalHT + prixTotalHT * 0.1;
		
		double TVA = prixTotalHT*0.2;
		
		System.out.println("Voici la note à payer : \n");
		for (Produit produit : panier) {
			System.out.println("Produit ; '" + produit.nom + "'\nPrix unitaire HT : " + produit.prix + "€\nPrix total HT : "
			+ prixTotalHT + " €\nTVA : " + TVA + " €\nPrix TTC : " + prixTotalTTC + "€");
		}
	}
	
	public void menuNoteClient(String lettre, Scanner sc, StockRestaurant SR) {
		System.out.println("Ajout d'un produit au panier du client. 'N' pour revenir au menu.");
		while (!(lettre = sc.next()).equals("N")){
			
			switch (lettre){
				
				// On affiche la liste des opérations disponibles
				case "h": 	System.out.println("a: Ajouter un produit à la note du client\n'N': Revenir au menu principal\nl: Afficher la note du client\n"); break;
						
				case "a": ajouterProduitNoteClient(sc, SR); break;
							
				case "p": afficherNoteAPayer(); break;
						
				default: 	System.out.println("Unknow command. Type h for help"); break;
			}
		}		
		
	}
}
