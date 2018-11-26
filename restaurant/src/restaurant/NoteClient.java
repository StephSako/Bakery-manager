package restaurant; import java.text.DecimalFormat; import java.util.LinkedList; import logger.*;

public class NoteClient implements NoteClientInterface{
	public LinkedList<Produit> panier = new LinkedList<Produit>();
	public String nomClient;
	public double prixTotalHT, prixTotalTTC, TVATotale;
	public DecimalFormat df = new DecimalFormat("0.00");
	public static Saisie saisie = new Saisie();
	public ConsoleLogger logger = new ConsoleLogger();
	public boolean remise;
	public static double valRemise = 0.1, TauxTVA = 0.1;
	
	public NoteClient(String nomClient) {
		this.nomClient = nomClient;
		this.TVATotale = this.prixTotalHT = this.prixTotalTTC = 0.0;
		this.remise = false;
	}
	public Produit existenceProduitEtAjout(Restaurant restaurant, String nom, double prix, int stock) {
		int j; boolean existe = false;
		do { j = 0; nom = saisie.getSaisieString();
			while(j < restaurant.stock.size()) {
				if (restaurant.stock.get(j).nom.equals(nom)) {
					prix = restaurant.stock.get(j).prix;
					nom = restaurant.stock.get(j).nom;
					existe = true;
					logger.error("PROGRAM", "Le produit " + nom + " existe dans le stock", false);
				} j++;
			} if (!existe) logger.error("PROGRAM", "Ce produit n'existe pas ...\nRetapez le produit :", true);
		} while (nom.equals("") || !existe);
		return new ProduitStockFinis(nom, prix, stock);
	}
	public void enleverProduitDuStock(Restaurant restaurant, Produit newProduit) {
		for (Produit produitRestau : restaurant.stock) {
			if (produitRestau.nom == newProduit.nom && !(produitRestau instanceof ProduitStockInfinis)) {
				if (newProduit.stock >= produitRestau.stock) { // Si le client est trop gourmand ...
					newProduit.stock = produitRestau.stock; // On ajoute qu'avec les dernieres ressources disponibles
					logger.info("PROGRAM", newProduit.stock+" "+newProduit.nom+" ajoute(s) (en rupture de stock)", true);
					restaurant.stock.remove(produitRestau); // Le produit devient en rupture de stock : on le supprime du stock
				}
				else if (newProduit.stock < produitRestau.stock) {
					produitRestau.stock -= newProduit.stock;
					logger.info("PROGRAM", newProduit.stock+" "+newProduit.nom+" retires du stock. Il en reste "+produitRestau.stock, true);
				} break;
			}
		} logger.info("PROGRAM", "\nMerci ! La commande a ete enregistree.", false);
	}
	public void produitDejaCommande(Restaurant restaurant, Produit newProduit) {
		int m = 0; boolean alreadyCommanded = false;
		while(m < panier.size()) {
			if (panier.get(m).nom.equals(newProduit.nom)) {
				panier.get(m).stock += newProduit.stock;
				alreadyCommanded = true;
				logger.info("PROGRAM", "Le produit "+newProduit.nom+" a deja ete commande", false);
				break;
			} m++;
		} if (!alreadyCommanded) this.panier.add(newProduit);
		logger.info("PROGRAM", "Le produit "+newProduit.nom+" a ete ajoute au panier", false);
	}
	public void ajouterProduitNoteClient(Restaurant restaurant) {
		String nom = ""; double prix = 0; int stock = 0;
		logger.info("OUTPUT", restaurant.afficherStock(), true);
		Produit newProduit = existenceProduitEtAjout(restaurant, nom, prix, stock); // Verifie que le produit existe et on cree le produit
		newProduit.stock = saisie.getSaisieInt("Nombre de " + newProduit.nom + " a ajouter au panier : ", "Montant incorrect ! Entrez un entier"); // On initialise le stock
		enleverProduitDuStock(restaurant, newProduit); // On retire le produit du stock du restaurant
		produitDejaCommande(restaurant, newProduit); // On ajoute le produit au panier du client sinon on l'additionne
		calculPrix(); // On calcule sa note
	}
	public void calculPrix() {
		for (Produit produit : panier) this.prixTotalHT = this.prixTotalHT + (produit.prix * produit.stock); // Calcul du prix total HT
		if (this.remise) this.prixTotalHT -= this.prixTotalHT*valRemise; // Calcul de la remise, s'il y en a une
		this.prixTotalTTC = this.prixTotalHT + this.prixTotalHT * TauxTVA; // Calcul du prix total TTC
		this.TVATotale = this.prixTotalHT * TauxTVA; // Calcul de la TVA totale encaissee
		logger.info("PROGRAM", "Calcul du prix total HT : " + df.format(prixTotalHT) + "\nCalcul de la TVA totale : "+df.format(TVATotale)+"\nCalcul du prix total TTC : "+df.format(prixTotalTTC), false);
	}
	public String afficherNoteAPayer() {
		String noteToPrint = "";
		noteToPrint += "\nVoici la note a payer : \n"; // On affiche la note a payer
		for (Produit produit : panier) noteToPrint += "Produit : '" + produit.nom + "' - " + produit.stock + " unites\nPrix unitaire HT : " + df.format(produit.prix) + " Euros\n-------------------------------\n";
		if (this.remise) noteToPrint += "Remise : "+valRemise*100+"%\n"; // Notification si remise
		noteToPrint += "Prix total HT : " + df.format(prixTotalHT) + " Euros\nTVA totale : " + df.format(TVATotale) + " Euros\nPrix TTC : " + df.format(prixTotalTTC) + " Euros\n";
		logger.info("PROGRAM", "La note est affichee", false);
		return noteToPrint;
	}
	public void cloturerNoteClient(Restaurant restaurant) {
		restaurant.ajoutertotalTVAFacturee(this.TVATotale); // On ajoute le montant total et la TVA encaissee dans les champs du restaurant
		logger.info("PROGRAM", "Ajout de "+TVATotale+" (TVA), donnees comptables", false);
		restaurant.ajouterRentreeArgent(this.prixTotalTTC);
		logger.info("PROGRAM", "Ajout de "+prixTotalTTC+" (prix TTC), donnees comptables", false);
		logger.info("INPUT", "Le client dispose-t-il d'une remise de 10% ? ('o' pour confirmer)", true);
		String valider = saisie.getSaisieString(); int h = 0;
		this.remise = (valider.toLowerCase().equals("o"));
		logger.info("OUTPUT", this.afficherNoteAPayer(), true);// On affiche la note
		while(h < restaurant.notesClientsActives.size()) {
			if (restaurant.notesClientsActives.get(h).nomClient == this.nomClient) {
				restaurant.notesClientsActives.remove(h); // On supprime la note encaissee
				logger.info("PROGRAM", "La note "+nomClient+" n'est plus active", false); break;
			} h++;
		}	
	}
}