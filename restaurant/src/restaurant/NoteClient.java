package restaurant;
import java.util.LinkedList;
import java.util.Scanner;

public class NoteClient {
	
	public LinkedList<Produit> panier = new LinkedList<Produit>();
	public int idClient;
	public double prixTotalHT;
	public double prixTotalTTC;
	public double TVATotale;
	public static double TauxTVA = 0.1;
	
	public NoteClient(int idClient) {
		this.idClient = idClient;
		this.prixTotalTTC = 0.0;
		this.prixTotalHT = 0.0;
		this.TVATotale = 0.0;
	}
	
	public void ajouterProduitNoteClient(Scanner sc, Restaurant SR, ConsoleLogger logger){
		String nom = "";
		double prix = 0;
		int stock;
		
		int j = 0;
		boolean existe = false;
		logger.print("Saisir le produit à ajouter : ");
		// On vérifie que le produit existe bien dans le stock
		/* !!!!*/
		
		do {
			j = 0;
			nom = sc.next();
			
			while(j < SR.stock.size()) {
				logger.print(SR.stock.get(j).nom);
				if (SR.stock.get(j).nom.equals(nom)) {
					prix = SR.stock.get(j).prix;
					existe = true;
					break;
				}
				j++;
			}
			if (!existe) logger.print("Ce produit n'existe pas ...\n Retapez le produit :");
			else logger.print("Ce produit est correct !\n");
			
		} while (nom.equals("") || !existe);
		
		logger.print("Nombre de " + nom + " à ajouter au panier : ");
		while ((stock = sc.nextInt()) <= 0){
			logger.print("Montant saisie incorrect !\n");
		}
		
		// On ajoute le produit saisie au panier du client
		this.panier.add(new Produit(nom, prix, stock));
		
		logger.print("\nMerci ! La commande a bien été enregistrée.\n");
	}
	
	public String afficherNoteAPayer() {
		String noteToPrint = "";
		
		// Calcul du prix total HT
		for (Produit produit : panier) {
			this.prixTotalHT = this.prixTotalHT + produit.prix;
		}
		
		// Calcul du prix total TTC
		this.prixTotalTTC = this.prixTotalHT + this.prixTotalHT * TauxTVA;
		
		// Calcul de la TVA totale encaissée
		this.TVATotale = this.prixTotalHT * TauxTVA;
		
		// On affiche la note à payer
		noteToPrint += "\nVoici la note à payer : \n";
		for (Produit produit : panier) {
			noteToPrint += "Produit ; '" + produit.nom + "'\nPrix unitaire HT : " + produit.prix + "€\nPrix total HT : "
			+ prixTotalHT + " €\nTVA totale: " + TVATotale + " €\nPrix TTC : " + prixTotalTTC + "€\n";
		}
		
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
