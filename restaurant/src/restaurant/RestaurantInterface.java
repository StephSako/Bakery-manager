package restaurant;

public interface RestaurantInterface {

	
	public void existenceClientEtAjout(String newClient);
	
	public void ajouterProduitStockRestaurant();

	public String afficherStock();
	
	public NoteClient ouvrirNote();
	
	public String afficherNotes();
	
	public void ajouterRentreeArgent(double rentreeArgent);
	
	public void ajoutertotalTVAFacturee(double totalTVAfacturee);
	
	public String donneesComptable();
}
