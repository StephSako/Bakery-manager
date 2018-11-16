package restaurant;

public abstract class Produit {

	// CHAMPS
	
	public String nom;
	public double prix;
	public int stock;
	
	public Produit(String nom, double prix, int stock) {
		this.nom = nom;
		this.prix = prix;
		this.stock = stock;
	}
}
