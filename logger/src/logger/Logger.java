package logger;

public interface Logger {
	public void info(String category, String message, boolean affichage);
	public void error(String category, String message, boolean affichage);
}
