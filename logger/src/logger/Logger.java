package logger;

public interface Logger {
	public void info(String category, String message);
	public void error(String category, String message);
}
