package restaurant;
import java.util.LinkedList;
import java.util.Scanner;

public class StockRestaurant {
	
	public LinkedList<Produit> stock = new LinkedList<Produit>();
	public String nom;
	public double rentreeArgent;
	public double totalTVAfacturee;
	
	public StockRestaurant(String nom) {
		this.nom = nom;
		this.totalTVAfacturee = 0.0;
		this.rentreeArgent = 0.0;
	}
	
	public void ajouterProduitStockRestaurant(Scanner sc){
		String nom;
		double prix;
		int stock;
		
		System.out.println("Saisir un nom : ");
		while ((nom = sc.next()).equals("")){
			System.out.println("Nom incorrect !\n");
		}
		
		System.out.println("Saisir un prix : ");
		while ((prix = sc.nextDouble()) <= 0){
			System.out.println("Prix incorrect !\n");
		}
		
		System.out.println("Saisir un montant à ajouter dans le stock : ");
		while ((stock = sc.nextInt()) <= 0){
			System.out.println("Stock incorrect !\n");
		}
		
		this.stock.add(new Produit(nom, prix, stock));
	}
	
	public void afficherStock() {
		for (Produit produit : stock) {
			System.out.println("'" + produit.nom + "' - " + produit.prix + "€ - " + produit.stock + " en stock");
		}
	}
}
