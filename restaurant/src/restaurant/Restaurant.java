package restaurant;
import java.util.LinkedList;
import java.util.Scanner;

public class Restaurant {
	
	// Liste des produits en stock
	public LinkedList<Produit> stock = new LinkedList<Produit>();
	
	// Liste des notes de clients actives/ouvertes/crées
	public LinkedList<NoteClient> notesClientsActives = new LinkedList<NoteClient>();
	
	// Champs de la classe Restaurant
	public String nom;
	public double rentreeArgent;
	public double totalTVAfacturee;
	
	public void ajouterRentreeArgent(double rentreeArgent) {
		this.rentreeArgent += rentreeArgent;
	}
	
	public void ajoutertotalTVAfacturee(double totalTVAfacturee) {
		this.totalTVAfacturee += totalTVAfacturee;
	}
	
	public Restaurant(String nom) {
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
	
	public NoteClient ouvrirNote(Scanner sc) {
		
		int idClientSearched;
		System.out.println("Saisissez l'identifiant du client : ");
		while ((idClientSearched = sc.nextInt()) < 0){
			System.out.println("Identifiant client incorrect !\n");
		}
		
		int i = 0;
		while(i < this.notesClientsActives.size()){
			if (this.notesClientsActives.get(i).idClient == idClientSearched) {
				return this.notesClientsActives.get(i);
			}
			i++;
		}
		return null;
	}
	
	public void afficherNotes() {
		for (NoteClient notes : this.notesClientsActives) {
			System.out.println("ID Client : " + notes.idClient);
			notes.afficherNoteAPayer();
			System.out.println("\n");
		}
	}
}
