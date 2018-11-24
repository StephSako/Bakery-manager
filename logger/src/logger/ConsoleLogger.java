package logger;

public class ConsoleLogger implements Logger{
	
	public LogFileWriter lfw = new LogFileWriter();

	public void info(String category, String message, boolean affichage) {
		if(affichage) System.out.println(message);
		lfw.ecrireFinLogFile(category, "INFO", message);
	}

	public void error(String category, String message, boolean affichage) {
		if(affichage) System.err.println(message);
		lfw.ecrireFinLogFile(category, "ERROR", message);
	}
}