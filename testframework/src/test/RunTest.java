package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import logger.*;

public class RunTest {
	
	public static ConsoleLogger logger = new ConsoleLogger();
	
	@SuppressWarnings({ "deprecation" })
	public static void main(String[] args) throws NoSuchMethodException {

		//change un objet String en objet Class
		String className = "restaurant.NotesClient";
		try {
		    Class<?> clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
		    logger.error("PROGRAM", "La classe n'a pas ete trouvee", true);
		}
		
		//liste les methodes d'une classe
		Class<?> uneClasse = String.class;
		for(Method method : uneClasse.getDeclaredMethods()) {
		    System.out.println(method.getName());
		}
		
		//execute le code pointe par un objet Method
		Object instance = null;
		try {
		    instance = uneClasse.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
		    logger.error("PROGRAM", "Erreur lors de la creation d'instance d'une classe", true);
		}

		Method method = uneClasse.getDeclaredMethod("", uneClasse);
		try {
		    method.invoke(instance);
		} catch (InvocationTargetException | IllegalAccessException e) {
		    logger.error("PROGRAM", "Erreur lors de l'appel des methodes", true);
		}
	}

}
