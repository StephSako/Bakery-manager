package restaurant;
import logger.*;

public class Operation {
	
	public Operation() {}
	
	public void casA (Logger logger, Restaurant stockRestaurant) {
		logger.info("PROGRAM", stockRestaurant.afficherStock(), true);
	}
	
	public void casB (Logger logger, Restaurant stockRestaurant) {
		stockRestaurant.ajouterProduitStockRestaurant();
	}
	
	public void casC (Restaurant stockRestaurant) {
		stockRestaurant.existenceClientEtAjout("");
	}
	
	public void casD (Logger logger, Restaurant stockRestaurant) {
		// On recherche la note du client si elle existe grace a son nom ce qui permet d'ouvrir plusieurs notes simultanement				
		logger.info("OUTPUT", "Saisissez le nom du client : ", true);
		NoteClient noteRecovered = stockRestaurant.ouvrirNote();
		if (noteRecovered == null) {
			logger.error("PROGRAM", "Aucune note n'a ete creee a ce nom", true);
		}
		else {
			// On saisie le produit a ajouter dans la note du client recuperee
			logger.info("OUTPUT", "Saisir le produit a ajouter parmi : ", true);
			noteRecovered.ajouterProduitNoteClient(stockRestaurant);
		}
	}
	
	public void casE (Logger logger, Restaurant stockRestaurant) {
		logger.info("OUTPUT", "Saisissez le nom du client : ", true);
		NoteClient noteToPrint = stockRestaurant.ouvrirNote();
		if (noteToPrint == null) {
			logger.error("PROGRAM", "Aucune note n'a ete creee avec ce nom", true);
		} else {
			// On affiche la note du client
			logger.info("PROGRAM", noteToPrint.afficherNoteAPayer(), true);
		}
	}
	
	public void casF (Logger logger, Restaurant stockRestaurant) {
		logger.info("OUTPUT", "Saisissez le nom du client : ", true);
		NoteClient noteToFence = stockRestaurant.ouvrirNote();
		if (noteToFence == null) {
			logger.error("PROGRAM", "Aucune note n'a ete creee avec ce nom", true);
		} else {
			// On affiche la note du client
			noteToFence.cloturerNoteClient(stockRestaurant);
			logger.info("PROGRAM", "Merci ! La note a bien ete encaissee.\n", true);
		}
	}	
	
	public void casG (Logger logger, Restaurant stockRestaurant) {
		logger.info("PROGRAM", stockRestaurant.afficherNotes(), true);
	}
	
	public void casH (Logger logger) {
		logger.info("PROGRAM", "'h' : Afficher cette fenetre d'aide.\n'a' : Afficher le stock du restaurant\n'b' : Ajouter un produit au stock du restaurant" +
		"\n'c' : Creer une note d'un client\n'd' : Ajouter un produit dans une note de client\n'e' : Afficher la note d'un client\n" + 
		"'f' : Cloturer et faire payer la note d'un client\n'g' : Afficher toutes les notes actives\n'i' : Afficher les donnees comptables\n'q' : Quitter le programme", true);
	}
	
	public void casI (Logger logger, Restaurant stockRestaurant) {
		logger.info("PROGRAM", stockRestaurant.donneesComptable(), true);
	}
	
	public void casQ (Logger logger) {
		logger.info("PROGRAM", "Vous quittez la caisse enregistreuse ...", true);
		System.exit(1); //faire quitter la caisse enregistreuse
	}	
}
