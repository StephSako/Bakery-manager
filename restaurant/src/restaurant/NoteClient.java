package restaurant;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import logger.*;

public class NoteClient implements NoteClientI{
	
	public LinkedList<Produit> panier = new LinkedList<Produit>();
	public int idClient;
	public double prixTotalHT;
	public double prixTotalTTC;
	public double TVATotale;
	public static double TauxTVA = 0.1;
	DecimalFormat df = new DecimalFormat("0.00");
	public static Saisie saisie = new Saisie();
	
	public NoteClient(int idClient) {
		this.idClient = idClient;
		this.prixTotalTTC = 0.0;
		this.prixTotalHT = 0.0;
		this.TVATotale = 0.0;
	}
	
	public void ajouterProduitNoteClient(Scanner sc, Restaurant SR, ConsoleLogger logger) {
		String nom = ""; double prix = 0; int stock = 0;
		
		int j = 0; boolean existe = false;
		logger.info("OUTPUT", "Saisir le produit a ajouter parmi : ");
		for (Produit produit : SR.stock) logger.info("OUTPUT", produit.nom + " - " + produit.stock + " unités");
		
		// On verifie que le produit existe bien dans le stock
		do {
			j = 0; nom = sc.next();
			nom = nom.trim();
			while(j < SR.stock.size()) {
				if (SR.stock.get(j).nom.equals(nom)) {
					prix = SR.stock.get(j).prix;
					existe = true;
					break;
				}
				j++;
			}
			if (!existe) logger.error("OUTPUT", "Ce produit n'existe pas ...\n Retapez le produit :");			
		} while (nom.equals("") || !existe);
		
		stock = saisie.getSaisieInt(sc, logger, "Nombre de " + nom + " a ajouter au panier : ");
		
		// On ajoute le produit au panier du client s'il n'en a pas deja commande, sinon on additionne son stock dans le panier
		int m = 0; boolean alreadyCommanded = false;
		while(m < panier.size()) {
			if (panier.get(m).nom.equals(nom)) {
				panier.get(m).stock += stock;
				alreadyCommanded = true;
				break;
			} m++;
		}
		Produit newProduit = new Produit(nom, prix, stock);
		
		// On retire le produit du stock du restaurant
		for (Produit produitRestau : SR.stock) {
			if (produitRestau.nom == produitRestau.nom) {
				
				if (newProduit.stock >= produitRestau.stock) { // Si le client est trop gourmand ...
					newProduit.stock = produitRestau.stock; // On ajoute qu'avec les dernières ressources disponibles
					SR.stock.remove(produitRestau); // Le produit devient en rupture de stock : on le supprime du stock
				}
				else if (newProduit.stock < produitRestau.stock) {
					produitRestau.stock -= newProduit.stock;
				}
				break;
			}
		}
		
		if (!alreadyCommanded) this.panier.add(newProduit);
		logger.info("OUTPUT", "\nMerci ! La commande a bien ete enregistree.\n");
	}
	
	public String afficherNoteAPayer() {
		String noteToPrint = "";
		
		// Calcul du prix total HT
		for (Produit produit : panier) this.prixTotalHT = this.prixTotalHT + (produit.prix * produit.stock);
		// Calcul du prix total TTC
		this.prixTotalTTC = this.prixTotalHT + this.prixTotalHT * TauxTVA;
		// Calcul de la TVA totale encaissee
		this.TVATotale = this.prixTotalHT * TauxTVA;
		
		// On affiche la note a payer
		noteToPrint += "\nVoici la note a payer : \n";
		for (Produit produit : panier) {
			noteToPrint += "Produit : '" + produit.nom + "' - " + produit.stock + " unites\nPrix unitaire HT : " + df.format(produit.prix) + " Euros\n-------------------------------\n";
		}
		noteToPrint += "Prix total HT : " + df.format(prixTotalHT) + " Euros\nTVA totale : " + df.format(TVATotale) + " Euros\nPrix TTC : " + df.format(prixTotalTTC) + " Euros\n";
		return noteToPrint;
	}
	
	public void cloturerNoteClient(Restaurant restaurant, ConsoleLogger logger) {
		
		// On ajoute le montant total et la TVA encaissee dans les champs du restaurant
		restaurant.ajoutertotalTVAfacturee(this.TVATotale);
		restaurant.ajouterRentreeArgent(this.prixTotalTTC);
				
		// On supprime la note dans la liste des notes actives de la caisse
		int h = 0;
		while(h < restaurant.notesClientsActives.size()) {
			if (restaurant.notesClientsActives.get(h).idClient == this.idClient) {
				restaurant.notesClientsActives.remove(h); // On supprime la note encaissee
				break;
			} h++;
		}
		
		logger.info("PROGRAM", "Merci ! La note a bien ete encaissee.");
	}
}