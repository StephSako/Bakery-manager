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
	public DecimalFormat df = new DecimalFormat("0.00");
	public Saisie saisie = new Saisie();
	public LogFileWriter lfw = new LogFileWriter();
	
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
			newClient = saisie.getSaisieString(sc, logger);
			lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+newClient);
			while(j < notesClientsActives.size()) {
				if(notesClientsActives.get(j).nomClient.equals(newClient)) {
					existe = true;
				} else { existe = false; }
				j++;
			}
			if(existe) {
				logger.info("OUTPUT", "Ce client existe deja !");
				lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le client "+newClient+" existe deja");
			}
		} while(newClient.equals("") || existe);
		
		// On ajoute la nouvelle note dans la liste des notes de clients encore actives
		notesClientsActives.add(new NoteClient(newClient));
		logger.info("PROGRAM", "Le client '" + newClient + "' a bien ete cree.");
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le client "+newClient+" est cree");
	}
	
	public void ajouterProduitStockRestaurant(){
		String lettre;
		do {
			logger.info("OUTPUT", "Le produit a-t-il un stock fini ? 'o'/'n'");
			lettre = (sc.next()).trim();
			lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+lettre);
		} while (!(lettre.equals("o")) && !(lettre.equals("n")));
		
		logger.info("OUTPUT", "Nom du produit a ajouter :");
		String nom = saisie.getSaisieString(sc, logger);
		lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+nom);
		
		double prix = saisie.getSaisieDouble(sc, logger, "Saisir un prix : ", "Prix incorrect ! Utilisez la virgule pour les centimes");
		lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+prix);
		
		// On cree le bon objet en fonction de son stock fini ou infini
		if (lettre.equals("o")){
			int stock = saisie.getSaisieInt(sc, logger, "Saisir un montant a ajouter dans le stock : ", "Montant incorrect ! Entrez un entier");
			lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+stock);
			this.stock.add(new ProduitStockFinis(nom, prix, stock));
		}
		else this.stock.add(new ProduitStockInfinis(nom, prix));
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le produit "+nom+" a ete ajoute au stock du restaurant");
	}
	
	public String afficherStock() {
		String stockToPrint = "";
		for (Produit produit : stock) {
			stockToPrint += produit.nom + " - " + df.format((produit.prix * 1.1)) + " euros TTC - ";
			if (produit instanceof ProduitStockInfinis) stockToPrint += "stock illimite\n";
			else stockToPrint += produit.stock + " unites\n";
		}
	lfw.ecrireFinLogFile("PROGRAM", "INFO", "Le stock du restaurant est affiche");
		return stockToPrint;
	}
	
	public NoteClient ouvrirNote() {
		String nomClientSearched = saisie.getSaisieString(sc, logger);
		lfw.ecrireFinLogFile("INPUT", "INFO", "L'utilisateur a tape "+nomClientSearched);
		
		int i = 0;
		while(i < this.notesClientsActives.size()){
			if (this.notesClientsActives.get(i).nomClient.equals(nomClientSearched)) {
				lfw.ecrireFinLogFile("PROGRAM", "INFO", "La note "+nomClientSearched+" est recuperee");
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
		if (notesToPrint == "") {
			lfw.ecrireFinLogFile("PROGRAM", "INFO", "Aucune note trouvee");
			return "Il n'y a aucune note en cours.";
		}
		return notesToPrint;
	}
	
	public void ajouterRentreeArgent(double rentreeArgent){ this.rentreeArgent += rentreeArgent;}
	public void ajoutertotalTVAfacturee(double totalTVAfacturee){ this.totalTVAfacturee += totalTVAfacturee;}
	
	public String donneesComptable() {
		lfw.ecrireFinLogFile("PROGRAM", "INFO", "Les donnees comptables sont affichees");
		return "Total des rentrees d'argent : "+df.format(rentreeArgent)+" Euros\nTotal de la TVA facturee : " + df.format(totalTVAfacturee) + " Euros.\n";
	}
}
