package restaurant;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import logger.*;

public class NoteClient implements NoteClientI{

	// CHAMPS
	
	public LinkedList<Produit> panier = new LinkedList<Produit>();
	public String nomClient;
	public double prixTotalHT;
	public double prixTotalTTC;
	public double TVATotale;
	public static double TauxTVA = 0.1;
	DecimalFormat df = new DecimalFormat("0.00");
	public static Saisie saisie = new Saisie();
	public Scanner sc = new Scanner(System.in);
	public ConsoleLogger logger = new ConsoleLogger();
	
	
	// CONSTRUCTEUR
	
	public NoteClient(String nomClient) {
		this.nomClient = nomClient;
		this.prixTotalTTC = 0.0;
		this.prixTotalHT = 0.0;
		this.TVATotale = 0.0;
	}
	
	
	// METHODES
	
	
	public Produit existenceProduitEtAjout(Restaurant restaurant, String nom, double prix, int stock) {
		int j; boolean existe = false;
		do {
			j = 0; nom = sc.next();
			nom = nom.trim();
			while(j < restaurant.stock.size()) {
				if (restaurant.stock.get(j).nom.equals(nom)) {
					prix = restaurant.stock.get(j).prix;
					nom = restaurant.stock.get(j).nom;
					existe = true;
				} j++;
			}
			if (!existe) logger.error("PROGRAM", "Ce produit n'existe pas ...\nRetapez le produit :");			
		} while (nom.equals("") || !existe);
		Produit newProduit = new Produit(nom, prix, stock);
		return newProduit;
	}
	
	public void enleverProduitDuStock(Restaurant restaurant, Produit newProduit) {
		for (Produit produitRestau : restaurant.stock) {
			if (produitRestau.nom == newProduit.nom) {
				if (newProduit.stock >= produitRestau.stock) { // Si le client est trop gourmand ...
					newProduit.stock = produitRestau.stock; // On ajoute qu'avec les dernieres ressources disponibles
					logger.info("PROGRAM", "\nIl n'y a pas assez de "+newProduit.nom+".\nVotre commande comportera seulement "+newProduit.stock+" "+newProduit.nom+"(s).\n");
					restaurant.stock.remove(produitRestau); // Le produit devient en rupture de stock : on le supprime du stock
				}
				else if (newProduit.stock < produitRestau.stock) {
					logger.info("PROGRAM", "\nMerci ! La commande a ete enregistree.\n");
					if(!(newProduit.nom.equals("Cafe"))) produitRestau.stock -= newProduit.stock;
				}
			}
		}
	}
	
	public void produitDejaCommande(Restaurant restaurant, Produit newProduit) {
		int m = 0; boolean alreadyCommanded = false;
		while(m < panier.size()) {
			if (panier.get(m).nom.equals(newProduit.nom)) {
				panier.get(m).stock += newProduit.stock;
				alreadyCommanded = true;
				break;
			} m++;
		}
		if (!alreadyCommanded) this.panier.add(newProduit);
	}
	
	public void ajouterProduitNoteClient(Restaurant restaurant) {
		String nom = ""; double prix = 0; int stock = 0;
		logger.info("OUTPUT", "Saisir le produit a ajouter parmi : ");
		for (Produit produit : restaurant.stock) logger.info("OUTPUT", produit.nom + " - " + produit.stock + " unites");
		
		// On verifie que le produit existe bien dans le stock et on cree le produit avec le bon prix et le bon nom
		Produit newProduit = existenceProduitEtAjout(restaurant, nom, prix, stock);
		
		//on initialise le stock (nb de produits commandes tapes par l'utilisateur)
		stock = saisie.getSaisieInt(sc, logger, "Nombre de " + newProduit.nom + " a ajouter au panier : ", "Montant incorrect ! Entrez un entier");
		newProduit.stock = stock;
		
		// On ajoute le produit au panier du client s'il n'en a pas deja commande, sinon on additionne son stock dans le panier
		produitDejaCommande(restaurant, newProduit);

		// On retire le produit du stock du restaurant
		enleverProduitDuStock(restaurant, newProduit); //marche pas
	}
	
	public String afficherNoteAPayer() { //ca affiche le mauvais stock
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
	
	public void cloturerNoteClient(Restaurant restaurant) {
		
		// On ajoute le montant total et la TVA encaissee dans les champs du restaurant
		restaurant.ajoutertotalTVAfacturee(this.TVATotale);
		restaurant.ajouterRentreeArgent(this.prixTotalTTC);
				
		// On supprime la note dans la liste des notes actives de la caisse
		int h = 0;
		while(h < restaurant.notesClientsActives.size()) {
			if (restaurant.notesClientsActives.get(h).nomClient == this.nomClient) {
				restaurant.notesClientsActives.remove(h); // On supprime la note encaissee
				break;
			} h++;
		}
		
		logger.info("PROGRAM", "Merci ! La note a bien ete encaissee.");
	}
}