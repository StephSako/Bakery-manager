package restaurant;

public interface NoteClientInterface {
	
	public Produit existenceProduitEtAjout(Restaurant restaurant, String nom, double prix, int stock);
	
	public void enleverProduitDuStock(Restaurant restaurant, Produit newProduit);
	
	public void produitDejaCommande(Restaurant restaurant, Produit newProduit);
	
	public void ajouterProduitNoteClient(Restaurant restaurant);
	
	public void calculPrix();
	
	public String afficherNoteAPayer();
	
	public void cloturerNoteClient(Restaurant restaurant);

}
