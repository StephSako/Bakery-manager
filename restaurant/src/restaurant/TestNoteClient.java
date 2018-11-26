package restaurant; import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class TestNoteClient {

	public NoteClient note = new NoteClient(null);
	public Restaurant restaurant = new Restaurant(null);
	public DecimalFormat df = new DecimalFormat("0.00");
	public LinkedList<Produit> panier = new LinkedList<Produit>();

	@Test
	public final void testExistenceProduitEtAjout() {
		int j = 0; boolean existe = false; String nom = ""; double prix = 0; int stock = 0;
		
		Produit produit = note.existenceProduitEtAjout(restaurant, nom, prix, stock);
		panier.add(produit);
		while(j < panier.size()) {
			if(panier.get(j).nom.equals(nom)) { existe = true; break; }
			else existe = false;
			j++;
		} assertFalse("Le produit existe deja", existe);
	}

	@Test
	public final void testEnleverProduitDuStock() {
		String nom = "Cookie"; double prix = 0.5; int stock = 15;
		Produit newProduit = new ProduitStockFinis(nom, prix, stock);

		stock -= 3;
		note.enleverProduitDuStock(restaurant, newProduit);
		assertEquals(stock, newProduit.stock);
		
		stock -= 0;
		note.enleverProduitDuStock(restaurant, newProduit);
		assertEquals(stock, newProduit.stock);
		
		stock -= -5;
		note.enleverProduitDuStock(restaurant, newProduit);
		assertEquals(stock, newProduit.stock);
	}

	@Test
	public final void testProduitDejaCommande() {
		int i = 0; Produit produitATrouver = new ProduitStockFinis("Glace", 2.5, 10);
		
		note.produitDejaCommande(restaurant, produitATrouver);
		while(i < panier.size()){
			if (panier.get(i).nom.equals("Glace")) {
				assertEquals(produitATrouver, panier.get(i));
			} i++;
		}
	}

	@Test
	public final void testAjouterProduitNoteClient() {
		NoteClient noteRecovered = restaurant.ouvrirNote();
		Produit newProduit = note.existenceProduitEtAjout(restaurant, "B", 1, 1);
		noteRecovered.enleverProduitDuStock(restaurant, newProduit);
		noteRecovered.produitDejaCommande(restaurant, newProduit);
		note.ajouterProduitNoteClient(restaurant);
		assertEquals(noteRecovered, note);
	}

	@Test
	public final void testCalculPrix() {
		double HT = 15; double TVA = 0.1; double TTC = HT * TVA;
		Produit produit = new ProduitStockFinis("Gateau", 15, 1);
		panier.add(produit);
		note.calculPrix();
		assertEquals(HT, note.prixTotalHT, 0.1);
		assertEquals(TTC, note.prixTotalTTC, 0.1);
		assertEquals(TVA, note.TVATotale, 0.1);
	}

	@Test
	public final void testAfficherNoteAPayer() {
		String noteToPrint = ""; double valRemise = 0.1; boolean remise = true; double prixTotalHT = 0; double TVATotale = 0; double prixTotalTTC = 0;
		noteToPrint += "\nVoici la note a payer : \n";
		for (Produit produit : panier) noteToPrint += "Produit : '" + produit.nom + "' - " + produit.stock + " unites\nPrix unitaire HT : " + df.format(produit.prix) + " Euros\n-------------------------------\n";
		if (remise) noteToPrint += "Remise : "+valRemise*100+"%\n";
		noteToPrint += "Prix total HT : " + df.format(prixTotalHT) + " Euros\nTVA totale : " + df.format(TVATotale) + " Euros\nPrix TTC : " + df.format(prixTotalTTC) + " Euros\n";
		note.afficherNoteAPayer();
		assertEquals(noteToPrint, note);
	}

	@Test
	public final void testCloturerNoteClient() {
		int i = 0; boolean toujoursla = false;
		NoteClient noteNotClosedYet = new NoteClient("Choucroute");
		noteNotClosedYet.cloturerNoteClient(restaurant);	
		while(i < restaurant.notesClientsActives.size()) {
			if (restaurant.notesClientsActives.get(i).nomClient == "Choucroute") {
				toujoursla = true;
			} i++;
		} assertFalse(toujoursla);
	}
}