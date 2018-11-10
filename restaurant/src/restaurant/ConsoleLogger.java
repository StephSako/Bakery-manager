package restaurant;


public class ConsoleLogger implements Logger{

	@Override
	public void print(String message) {
		System.out.println(message);		
	}

}