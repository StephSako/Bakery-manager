package restaurant;
import java.util.LinkedList;
import java.util.Scanner;
import logger.*;

public class Restaurant {
	
	// Liste des produits en stock
	public LinkedList<Produit> stock = new LinkedList<Produit>();
	
	// Liste des notes de clients actives/ouvertes/cr√©es
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
		
		System.out.println("Saisir un montant ‡† ajouter dans le stock : ");
		while ((stock = sc.nextInt()) <= 0){
			System.out.println("Stock incorrect !\n");
		}
		
		this.stock.add(new Produit(nom, prix, stock));
	}
	
	public String afficherStock() {
		String stockToPrint = "";
		for (Produit produit : stock) {
			stockToPrint += "'" + produit.nom + "' - " + produit.prix + "Ä - " + produit.stock + " en stock\n";
		}
		return stockToPrint;
	}
	
	public NoteClient ouvrirNote(Scanner sc, ConsoleLogger logger) {
		
		int idClientSearched;
		logger.info("OUTPUT", "Saisissez l'identifiant du client : ");
		while ((idClientSearched = sc.nextInt()) < 0){
			logger.error("OUTPUT", "Identifiant client incorrect !\n");
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
	
	public String afficherNotes() {
		String notesToPrint = "";
		for (NoteClient notes : this.notesClientsActives) {
			notesToPrint += "ID Client : " + notes.idClient;
			notesToPrint += notes.afficherNoteAPayer();
		}
		
		if (notesToPrint == "") return "Il n'y a aucune note.";
		return notesToPrint;
	}
}
