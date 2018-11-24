package restaurant;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import logger.*;

public class Restaurant implements RestaurantInterface {
	public Scanner sc = new Scanner(System.in);
	public ConsoleLogger logger = new ConsoleLogger();
	public LinkedList<Produit> stock = new LinkedList<Produit>(); // Liste des produits en stock
	public LinkedList<NoteClient> notesClientsActives = new LinkedList<NoteClient>(); // Liste des notes de clients actives/ouvertes/creees
	public String nom;
	public double rentreeArgent;
	public double totalTVAfacturee;
	public DecimalFormat df = new DecimalFormat("0.00");
	public Saisie saisie = new Saisie();
	
	public Restaurant(String nom) {
		this.nom = nom;
		this.totalTVAfacturee = 0.0;
		this.rentreeArgent = 0.0;
	}
	
	public void existenceClientEtAjout(String newClient) {
		int j; boolean existe = false;
		do { j = 0;
			logger.info("OUTPUT", "Saisissez le nom du client a creer : ", true);
			newClient = saisie.getSaisieString(sc, logger);
			while(j < notesClientsActives.size()) {
				if(notesClientsActives.get(j).nomClient.equals(newClient)) { existe = true; break; }
				else existe = false;
				j++;
			} if(existe) logger.info("OUTPUT", "Ce client existe deja !", true);
		} while(newClient.equals("") || existe);
		
		notesClientsActives.add(new NoteClient(newClient)); // On ajoute la nouvelle note dans la liste des notes de clients encore actives
		logger.info("PROGRAM", "Le client '" + newClient + "' a bien ete cree.", true);
	}
	
	public void ajouterProduitStockRestaurant(){
		String lettre;
		do { logger.info("OUTPUT", "Le produit a-t-il un stock fini ? 'o'/'n'", true);
			lettre = (sc.next()).trim();
		} while (!(lettre.equals("o")) && !(lettre.equals("n")));
		
		logger.info("OUTPUT", "Nom du produit a ajouter :", true);
		String nom = saisie.getSaisieString(sc, logger);
		double prix = saisie.getSaisieDouble(sc, logger, "Saisir un prix : ", "Prix incorrect ! Utilisez la virgule pour les centimes");
		
		if (lettre.equals("o")){ // On cree le bon objet en fonction de son stock fini ou infini
			int stock = saisie.getSaisieInt(sc, logger, "Saisir un montant a ajouter dans le stock : ", "Montant incorrect ! Entrez un entier");
			this.stock.add(new ProduitStockFinis(nom, prix, stock));
		}
		else this.stock.add(new ProduitStockInfinis(nom, prix));
		logger.info("OUTPUT", "Le produit "+nom+" a ete ajoute au stock du restaurant", false);
	}
	
	public String afficherStock() {
		String stockToPrint = "";
		for (Produit produit : stock) {
			stockToPrint += produit.nom + " - " + df.format((produit.prix * 1.1)) + " euros TTC - ";
			if (produit instanceof ProduitStockInfinis) stockToPrint += "stock illimite\n";
			else stockToPrint += produit.stock + " unites\n";
		}
		logger.info("PROGRAM", "Le stock du restaurant est affiche", false);
		return stockToPrint;
	}
	
	public NoteClient ouvrirNote() {
		String nomClientSearched = saisie.getSaisieString(sc, logger); int i = 0;
		while(i < this.notesClientsActives.size()){
			if (this.notesClientsActives.get(i).nomClient.equals(nomClientSearched)) {
				logger.info("PROGRAM", "La note "+nomClientSearched+" est recuperee", false);
				return this.notesClientsActives.get(i);
			} i++;
		} return null;
	}
	
	public String afficherNotes() {
		String notesToPrint = "";
		for (NoteClient notes : this.notesClientsActives) notesToPrint += "ID Client : " + notes.nomClient + notes.afficherNoteAPayer()+"\n";
		if (notesToPrint == "") {
			logger.info("PROGRAM", "Aucune note trouvee", true);
			return "Il n'y a aucune note en cours.";
		} return notesToPrint;
	}
	
	public void ajouterRentreeArgent(double rentreeArgent){ 
		if(rentreeArgent >= 0) this.rentreeArgent += rentreeArgent;
		else {logger.error("PROGRAM", "Le chiffre doit etre positif", true);}
	}
	
	public void ajoutertotalTVAFacturee(double totalTVAfacturee){ 
		if(totalTVAfacturee >= 0) this.totalTVAfacturee += totalTVAfacturee;
		else {logger.error("PROGRAM", "Le chiffre doit etre positif", true);}
	}
	
	public String donneesComptable() {
		logger.info("PROGRAM", "Les donnees comptables sont affichees", false);
		return "Total des rentrees d'argent : "+df.format(rentreeArgent)+" Euros\nTotal de la TVA facturee : " + df.format(totalTVAfacturee) + " Euros.\n";
	}
}
