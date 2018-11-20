package restaurant;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import logger.*;

public class NoteClient implements NoteClientInterface{

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
	public LogFileWriter lfw = new LogFileWriter();
	public boolean remise;
	public static double valRemise = 0.1;
	
	// CONSTRUCTEUR
	
	public NoteClient(String nomClient) {
		this.nomClient = nomClient;
		this.prixTotalTTC = 0.0;
		this.prixTotalHT = 0.0;
		this.TVATotale = 0.0;
		this.remise = false;
	}
	
	// METHODES
	
	public Produit existenceProduitEtAjout(Restaurant restaurant, String nom, double prix, int stock) {
		int j; boolean existe = false;
		do {
			j = 0;
			nom = saisie.getSaisieString(sc, logger);
			lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+nom);
			while(j < restaurant.stock.size()) {
				if (restaurant.stock.get(j).nom.equals(nom)) {
					prix = restaurant.stock.get(j).prix;
					nom = restaurant.stock.get(j).nom;
					existe = true;
					lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le produit "+nom+" existe dans le stock");
				} j++;
			}
			if (!existe) logger.error("PROGRAM", "Ce produit n'existe pas ...\nRetapez le produit :");			
		} while (nom.equals("") || !existe);
		Produit newProduit = new ProduitStockFinis(nom, prix, stock);
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le produit "+nom+" est cree");
		return newProduit;
	}
	
	public void enleverProduitDuStock(Restaurant restaurant, Produit newProduit) {
		for (Produit produitRestau : restaurant.stock) {
			if (produitRestau.nom == newProduit.nom && !(produitRestau instanceof ProduitStockInfinis)) {
				if (newProduit.stock >= produitRestau.stock) { // Si le client est trop gourmand ...
					newProduit.stock = produitRestau.stock; // On ajoute qu'avec les dernieres ressources disponibles
					logger.info("PROGRAM", newProduit.stock+" "+newProduit.nom+" ajoute(s) (rupture de stock)");
					restaurant.stock.remove(produitRestau); // Le produit devient en rupture de stock : on le supprime du stock
					lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le produit "+produitRestau.nom+" est en rupture de stock");
				}
				else if (newProduit.stock < produitRestau.stock) {
					produitRestau.stock -= newProduit.stock;
					lfw.ecrireFinLogFile("OUTPUT", "PROGRAM", newProduit.stock+" "+newProduit.nom+" retires du stock. Il en reste "+produitRestau.stock);
				} break;
			}
		}
		logger.info("PROGRAM", "\nMerci ! La commande a ete enregistree.");
	}
	
	public void produitDejaCommande(Restaurant restaurant, Produit newProduit) {
		int m = 0; boolean alreadyCommanded = false;
		while(m < panier.size()) {
			if (panier.get(m).nom.equals(newProduit.nom)) {
				panier.get(m).stock += newProduit.stock;
				alreadyCommanded = true;
				lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le produit "+newProduit.nom+" a deja ete commande");
				break;
			} m++;
		}
		if (!alreadyCommanded) this.panier.add(newProduit);
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le produit "+newProduit.nom+" a ete ajoute au panier");
	}
	
	public void ajouterProduitNoteClient(Restaurant restaurant) {
		String nom = ""; double prix = 0; int stock = 0;
		logger.info("OUTPUT", restaurant.afficherStock());

		// On verifie que le produit existe bien dans le stock et on cree le produit avec le bon prix et le bon nom
		Produit newProduit = existenceProduitEtAjout(restaurant, nom, prix, stock);
		
		//on initialise le stock (nb de produits commandes tapes par l'utilisateur)
		stock = saisie.getSaisieInt(sc, logger, "Nombre de " + newProduit.nom + " a ajouter au panier : ", "Montant incorrect ! Entrez un entier");
		newProduit.stock = stock;
		lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+stock);

		// On retire le produit du stock du restaurant
		enleverProduitDuStock(restaurant, newProduit);
		
		// On ajoute le produit au panier du client s'il n'en a pas deja commande, sinon on additionne son stock dans le panier
		produitDejaCommande(restaurant, newProduit);
		
		//on calcule sa note
		calculPrix();
	}
	
	public void calculPrix() {
		// Calcul du prix total HT
		for (Produit produit : panier) this.prixTotalHT = this.prixTotalHT + (produit.prix * produit.stock);
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Calcul du prix total HT : "+df.format(prixTotalHT));

		// Calcul de la remise, s'il y en a une
		if (this.remise) this.prixTotalHT -= this.prixTotalHT*valRemise;
		
		// Calcul du prix total TTC
		this.prixTotalTTC = this.prixTotalHT + this.prixTotalHT * TauxTVA;
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Calcul du prix total TTC : "+df.format(prixTotalTTC));
		
		// Calcul de la TVA totale encaissee
		this.TVATotale = this.prixTotalHT * TauxTVA;
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Calcul de la TVA totale : "+df.format(TVATotale));
	}
	
	public String afficherNoteAPayer() { //ca affiche le mauvais stock (??, a verfifier)
		String noteToPrint = "";
		// On affiche la note a payer
		noteToPrint += "\nVoici la note a payer : \n";
		for (Produit produit : panier) noteToPrint += "Produit : '" + produit.nom + "' - " + produit.stock + " unites\nPrix unitaire HT : " + df.format(produit.prix) + " Euros\n-------------------------------\n";
		
		// Notification si remise
		if (this.remise) noteToPrint += "Remise : "+valRemise*100+"%\n";
		
		noteToPrint += "Prix total HT : " + df.format(prixTotalHT) + " Euros\nTVA totale : " + df.format(TVATotale) + " Euros\nPrix TTC : " + df.format(prixTotalTTC) + " Euros\n";
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "La note est affichee");
		return noteToPrint;
	}
	
	public void cloturerNoteClient(Restaurant restaurant) {
		// On ajoute le montant total et la TVA encaissee dans les champs du restaurant
		restaurant.ajoutertotalTVAfacturee(this.TVATotale);
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Ajout de "+TVATotale+" (TVA), donnees comptables");
		restaurant.ajouterRentreeArgent(this.prixTotalTTC);
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Ajout de "+prixTotalTTC+" (prix TTC), donnees comptables");
		
		// On demande si le client dispose d'une remise de 10%
		logger.info("INPUT", "Le client dispose-t-il d'une remise de 10% ? ('o' pour confirmer)");
		String valider = saisie.getSaisieString(sc, logger);
		this.remise = (valider.toLowerCase().equals("o"));
		lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+valider);
		
		// On affiche la note
		logger.info("OUTPUT", this.afficherNoteAPayer());
		//ca affiche bien  la note MAIS CA LA RECALCULE AUSSI (note x2)
		
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "La note est affichee");
		
		// On supprime la note dans la liste des notes actives de la caisse
		int h = 0;
		while(h < restaurant.notesClientsActives.size()) {
			if (restaurant.notesClientsActives.get(h).nomClient == this.nomClient) {
				restaurant.notesClientsActives.remove(h); // On supprime la note encaissee
				lfw.ecrireFinLogFile("PROGRAM", "INFO", "La note "+nomClient+" n'est plus active");
				break;
			} h++;
		}	
	}
}