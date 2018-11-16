package restaurant;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;
import logger.*;

public class Restaurant {
	
	// CHAMPS
	
	public Scanner sc = new Scanner(System.in);
	public ConsoleLogger logger = new ConsoleLogger();
	// Liste des produits en stock
	public LinkedList<Produit> stock = new LinkedList<Produit>();
	// Liste des notes de clients actives/ouvertes/creees
	public LinkedList<NoteClient> notesClientsActives = new LinkedList<NoteClient>();
	public String nom;
	public double rentreeArgent;
	public double totalTVAfacturee;
	DecimalFormat df = new DecimalFormat("0.00");
	Saisie saisie = new Saisie();
	
	// CONSTRUCTEUR
	
	public Restaurant(String nom) {
		this.nom = nom;
		this.totalTVAfacturee = 0.0;
		this.rentreeArgent = 0.0;
	}
	
	// METHODES
	
	public void existenceClientEtAjout() {
		String newClient; int j; boolean existe = false;
		do {
			j = 0;
			logger.info("OUTPUT", "Saisissez le nom du client a creer : ");
			newClient = (sc.next()).trim();
			while(j < notesClientsActives.size()) {
				if(notesClientsActives.get(j).nomClient.equals(newClient)) {
					existe = true;
				} else { existe = false; }
				j++;
			}
			if(existe) logger.info("OUTPUT", "Ce client existe deja !");
		} while(newClient.equals("") || existe);
		
		// On ajoute la nouvelle note dans la liste des notes de clients encore actives
		notesClientsActives.add(new NoteClient(newClient));
		logger.info("PROGRAM", "Le client '" + newClient + "' a bien ete cree.");
	}
	
	public void ajouterProduitStockRestaurant(){
		logger.info("OUTPUT", "Le produit a-t-il un stock finis ? 'o'/'n'");
		String lettre = sc.next();
		
		while (!(lettre.equals("o")) && !(lettre.equals("n"))) {
			logger.error("OUTPUT", "Saisissez 'o' ou 'n' pour déterminer si le produit a un stock :");
			lettre = (sc.next()).trim();
		}
		
		logger.info("OUTPUT", "Nom du produit a ajouter :");
		String nom = (sc.next()).trim();
		double prix = saisie.getSaisieDouble(sc, logger, "Saisir un prix : ", "Prix incorrect ! Utilisez la virgule pour les centimes");
		
		// On créé en le bon objet en fonction de son stock fini ou infini
		if (lettre.equals("o")){
			int stock = saisie.getSaisieInt(sc, logger, "Saisir un montant a ajouter dans le stock : ", "Montant incorrect ! Entrez un entier");
			this.stock.add(new ProduitStockFinis(nom, prix, stock));
		}
		else this.stock.add(new ProduitStockInfinis(nom, prix));
	}
	
	public String afficherStock() {
		String stockToPrint = "";
		for (Produit produit : stock) {
			stockToPrint += produit.nom + " - " + df.format((produit.prix * 1.1)) + " euros TTC - ";
			if (produit instanceof ProduitStockInfinis) stockToPrint += "stock illimite\n";
			else stockToPrint += produit.stock + " unites\n";
		}
		return stockToPrint;
	}
	
	public NoteClient ouvrirNote() {
		String nomClientSearched = (sc.next()).trim();
		
		int i = 0;
		while(i < this.notesClientsActives.size()){
			if (this.notesClientsActives.get(i).nomClient.equals(nomClientSearched)) {
				return this.notesClientsActives.get(i);
			}
			i++;
		}
		return null;
	}
	
	public String afficherNotes() {
		String notesToPrint = "";
		for (NoteClient notes : this.notesClientsActives) {
			notesToPrint += "ID Client : " + notes.nomClient;
			notesToPrint += notes.afficherNoteAPayer()+"\n";
		}		
		if (notesToPrint == "") return "Il n'y a aucune note en cours.";
		return notesToPrint;
	}
	
	public void ajouterRentreeArgent(double rentreeArgent){ this.rentreeArgent += rentreeArgent; }
	public void ajoutertotalTVAfacturee(double totalTVAfacturee){ this.totalTVAfacturee += totalTVAfacturee; }
	
	public String donneesComptable() {
		return "Total des rentrees d'argent : "+df.format(rentreeArgent)+"\nTotal de la TVA facturee : "+df.format(totalTVAfacturee);
	}
}
