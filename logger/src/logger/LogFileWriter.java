package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogFileWriter {
	
	// CONSTRUCTEUR
	
	public LogFileWriter() {}
	
	
	// FONCTIONS
	
	public void ecrireFinLogFile(String categorie, String level, String message) {
		try	{			
			LocalDateTime now = LocalDateTime.now();
			String date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
			FileWriter fw = new FileWriter("logfile",true);
			fw.write("DATE("+date+") - CATEGORY (" + categorie + ") - LEVEL (" + level + ") - " + message + "\n");
			fw.close();
		}
		catch(IOException ioe){
			System.err.println(ioe.getMessage());
		}
	}
	
	public static Logger getLogger(String name) {
		
		return new ConsoleLogger();
	}
}
