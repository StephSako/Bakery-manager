package restaurant;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;
//import java.util.LinkedList;

import org.junit.jupiter.api.Test;

public class TestRestaurant {

	@Test
	public final void testExistenceClientEtAjout() {
			//liste
		//liste vide
		//liste remplie, le client n'existe pas
		//liste remplie, le client existe
		
			//string entree au clavier
		//newClient = ""
		//newClient = null
		//newClient = "abcdef"
		//newclient = "123456"
		
		//resultat : état de la liste

		fail("Not yet implemented");
	}

	@Test
	public final void testAjouterProduitStockRestaurant() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAfficherStock() {
		fail("Not yet implemented");
	}

	@Test
	public final void testOuvrirNote() {
		fail("Not yet implemented");
	}

	@Test
	public final void testAfficherNotes() {
		Restaurant restaurant = new Restaurant(null);
		//LinkedList<NoteClient> notesClientsActives = new LinkedList<NoteClient>();
		String resultat;
		
		//liste vide
		resultat = "Il n'y a aucune note en cours.";
		assertTrue("liste de notes vide", restaurant.afficherNotes() == resultat);

		//liste remplie
	}

	@Test
	public final void testAjouterRentreeArgent() {
		//argument rentreeArgent
		//0
		//-5
		//5
		fail("Not yet implemented");
	}

	@Test
	public final void testAjoutertotalTVAfacturee() {
		fail("Not yet implemented");
		//argument totalTVAFacturee
		//0
		//-5
		//5
	}

	@Test
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
