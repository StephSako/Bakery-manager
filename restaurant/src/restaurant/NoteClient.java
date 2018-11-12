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
	
	public NoteClient(int idClient) {
		this.idClient = idClient;
		this.prixTotalTTC = 0.0;
		this.prixTotalHT = 0.0;
		this.TVATotale = 0.0;
	}
	
	public void ajouterProduitNoteClient(Scanner sc, Restaurant SR, ConsoleLogger logger){
		String nom = ""; double prix = 0; int stock;
		
		int j = 0; boolean existe = false;
		logger.info("OUTPUT", "Saisir le produit à  ajouter parmi : ");
		for (Produit produit : SR.stock) logger.info("OUTPUT", produit.nom);
		
		// On vérifie que le produit existe bien dans le stock
		do {
			j = 0; nom = sc.next();
			
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
		
		logger.info("OUTPUT", "Nombre de " + nom + " à  ajouter au panier : ");
		while ((stock = sc.nextInt()) <= 0){
			logger.error("OUTPUT", "Montant saisie incorrect !\n");
		}
		
		// On ajoute le produit au panier du client s'il n'en a pas déjà  commandé, sinon on additionne son stock dans le panier
		int m = 0; boolean alreadyCommanded = false;
		while(m < panier.size()) {
			if (panier.get(m).nom.equals(nom)) {
				panier.get(m).stock += stock;
				alreadyCommanded = true;
				break;
			} m++;
		}
		if (!alreadyCommanded) this.panier.add(new Produit(nom, prix, stock));
		logger.info("OUTPUT", "\nMerci ! La commande a bien été enregistrée.\n");
	}
	
	public String afficherNoteAPayer() {
		String noteToPrint = "";
		
		// Calcul du prix total HT
		for (Produit produit : panier) this.prixTotalHT = this.prixTotalHT + (produit.prix * produit.stock);
		// Calcul du prix total TTC
		this.prixTotalTTC = this.prixTotalHT + this.prixTotalHT * TauxTVA;
		// Calcul de la TVA totale encaissée
		this.TVATotale = this.prixTotalHT * TauxTVA;
		
		// On affiche la note à  payer
		noteToPrint += "\nVoici la note à  payer : \n";
		for (Produit produit : panier) {
			noteToPrint += "Produit ; '" + produit.nom + "' - " + produit.stock + " unités\nPrix unitaire HT : " + df.format(produit.prix) + "€\n-------------------------------\n";
		}
		noteToPrint += "Prix total HT : " + df.format(prixTotalHT) + " €\nTVA totale : " + df.format(TVATotale) + " €\nPrix TTC : " + df.format(prixTotalTTC) + "€\n";
		return noteToPrint;
	}
	
	public void cloturerNoteClient(Restaurant restaurant) {
		// On retire le produit au stock du restaurant
		/*int j = 0;
		while(j < SR.stock.size()){
			if (SR.stock.get(j).nom == nom) {
				SR.stock.get(j).stock -= stock;
				break;
			}
			j++;
		}*/
		
		// On supprime la note dans la liste des notes actives de la caisse	
		
		// On ajoute le montant total et la TVA encaissée dans les champs du restaurant
		restaurant.ajoutertotalTVAfacturee(this.TVATotale);
		restaurant.ajouterRentreeArgent(this.prixTotalTTC);
	}
}
