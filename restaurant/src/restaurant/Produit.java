package restaurant;

import logger.ConsoleLogger;

public abstract class Produit {

	public ConsoleLogger logger = new ConsoleLogger();
	public String nom;
	public double prix;
	public int stock;
	
	public Produit(String nom, double prix, int stock) {
		this.nom = nom;
		this.prix = prix;
		this.stock = stock;
		logger.error("PROGRAM", "Le produit a stock fini a bien ete cree", false);	
	}
	
	public Produit(String nom, double prix) {
		this.nom = nom;
		this.prix = prix;
		logger.error("PROGRAM", "Le produit a stock infini a bien ete cree", false);
	}
}
