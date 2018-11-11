package restaurant;
import java.util.Scanner;

public interface NoteClientI {
		
	public void ajouterProduitNoteClient(Scanner sc, Restaurant SR, ConsoleLogger logger);
	public String afficherNoteAPayer();
	public void cloturerNoteClient(Restaurant restaurant);
}
