package logger;

public class ConsoleLogger implements Logger{

	public void info(String category, String message) {
		System.out.println(message);		
	}

	public void error(String category, String message) {
		System.err.println(message);
	}
}