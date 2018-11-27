package test;

import java.text.DecimalFormat;
import java.util.LinkedList;
import junit.framework.*;
import logger.ConsoleLogger;
import restaurant.*;

public class TestRestaurant  extends TestCase{

	public LinkedList<Produit> stock = new LinkedList<Produit>();
	public LinkedList<NoteClient> notesClientsActives = new LinkedList<NoteClient>();
	public ConsoleLogger logger = new ConsoleLogger();
	public Restaurant restaurant = new Restaurant(null);
	public DecimalFormat df = new DecimalFormat("0.00");
	
	public final void testExistenceClientEtAjout() {
		int j = 0; boolean existe = false; String newClient = "";
		
		restaurant.existenceClientEtAjout(newClient);
		notesClientsActives.add(new NoteClient(newClient));
		while(j < notesClientsActives.size()) {
			if(notesClientsActives.get(j).nomClient.equals(newClient)) { existe = true; break; }
			else existe = false;
			j++;
		}
		assertFalse("Le client existe deja", existe);
	}

	
	public final void testAjouterProduitStockRestaurant() {
		int i = 0; boolean produitCree = false; String nom = "";
		
		restaurant.ajouterProduitStockRestaurant();
		stock.add(new ProduitStockFinis(nom, 0.5, 20));
		while(i < stock.size()) {
			if(stock.get(i).nom.equals(nom)) { produitCree = true;}
			else produitCree = false;
			i++;
		}
		assertTrue("Le produit n'a pas ete cree", produitCree);
	}

	
	public final void testAfficherStock() {
		String stockAfficher = "";
		
		for (Produit produit : stock) {
			stockAfficher += produit.nom + " - " + df.format((produit.prix * 1.1)) + " euros TTC - ";
			if (produit instanceof ProduitStockInfinis) stockAfficher += "stock illimite\n";
			else stockAfficher += produit.stock + " unites\n";
		}
		assertEquals(stockAfficher, restaurant.afficherStock());
	}

	
	public final void testOuvrirNote() {
		NoteClient noteATrouver; int i = 0; String nomClientSearched = "";
		
		noteATrouver = restaurant.ouvrirNote();
		while(i < notesClientsActives.size()){
			if (notesClientsActives.get(i).nomClient.equals(nomClientSearched)) {
				assertEquals(noteATrouver, notesClientsActives.get(i));
			} i++;
		}
	}

	
	public final void testAfficherNotes() {
		String notesToPrint = "";
		
		for (NoteClient notes : this.notesClientsActives) notesToPrint += "ID Client : " + notes.nomClient + notes.afficherNoteAPayer()+"\n";
		assertEquals(notesToPrint, restaurant.afficherNotes());
	}

	
	public final void testAjouterRentreeArgent() {
		double recette = 0;
		
		recette = 5;
		recette += recette;
		restaurant.ajouterRentreeArgent(5);
		assertEquals(recette, restaurant.rentreeArgent, 0.01);
		
		recette = 0;
		recette += recette;
		restaurant.ajouterRentreeArgent(0);
		assertEquals(recette, restaurant.rentreeArgent, 0.01);
		
		recette = -3;
		recette += recette;
		restaurant.ajouterRentreeArgent(-3);
		assertEquals(recette, restaurant.rentreeArgent, 0.01);
	}

	
	public final void testAjoutertotalTVAfacturee() {
		double tva;
		
		tva = 5;
		tva += tva;
		restaurant.ajouterRentreeArgent(5);
		assertEquals(tva, restaurant.rentreeArgent, 0.01);
		
		tva = 0;
		tva += tva;
		restaurant.ajouterRentreeArgent(0);
		assertEquals(tva, restaurant.rentreeArgent, 0.01);
		
		tva = -3;
		tva += tva;
		restaurant.ajouterRentreeArgent(-3);
		assertEquals(tva, restaurant.rentreeArgent, 0.01);
	}

	
	public final void testDonneesComptable() {
		DecimalFormat df = new DecimalFormat("0.00");
		Restaurant restaurant = new Restaurant(null);
		double argent = 0;
		double tva = 0;
		String resultat;
		
		argent = 5;
		tva = 2;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent et tva positifs", restaurant.donneesComptable() == resultat);
		
		argent = 0;
		tva = 2;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent nul, tva positif", restaurant.donneesComptable() == resultat);
		
		argent = 5;
		tva = 0;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent positif, tva nul", restaurant.donneesComptable() == resultat);
		
		argent = 0;
		tva = 0;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent et tva nuls", restaurant.donneesComptable() == resultat);
		
		argent = -5;
		tva = 2;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent negatif, tva positif", restaurant.donneesComptable() == resultat);
		
		argent = 5;
		tva = -2;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent positif, tva negatif", restaurant.donneesComptable() == resultat);
		
		argent = -5;
		tva = -2;
		resultat = "Total des rentrees d'argent : "+df.format(argent)+" Euros\nTotal de la TVA facturee : " + df.format(tva) + " Euros.\n";
		assertTrue("argent et tva negatifs", restaurant.donneesComptable() == resultat);
	}
}