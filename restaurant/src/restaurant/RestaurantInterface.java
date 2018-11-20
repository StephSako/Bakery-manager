package restaurant;

public interface RestaurantInterface {

	
	public void existenceClientEtAjout();
	
	public void ajouterProduitStockRestaurant();

	public String afficherStock();
	
	public NoteClient ouvrirNote();
	
	public String afficherNotes();
	
	public void ajouterRentreeArgent(double rentreeArgent);
	
	public void ajoutertotalTVAfacturee(double totalTVAfacturee);
	
	public String donneesComptable();
}
