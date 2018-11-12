package logger;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogFileWriter {
	
	public LogFileWriter() {}
	
	public void ecrireFinLogFile(String categorie, String level, String message) {
		try	{			
			LocalDateTime now = LocalDateTime.now();
			String date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth() + " " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
			FileWriter fw = new FileWriter("/home/stephsako/ProjetPoo3A/logger/logfile",true);
			fw.write(date + " - CATEGORIE : " + categorie + " - LEVEL : " + level + " - " + message + "\n");
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
