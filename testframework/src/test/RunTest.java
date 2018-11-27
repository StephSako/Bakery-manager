package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTest {
		
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws NoSuchMethodException {

		try {
			String className1 = "test.TestNoteClient";
			String className2 = "test.TestRestaurant";
		
			Class<?> uneClasse1 = Class.forName(className1);
			Class<?> uneClasse2 = Class.forName(className2);
			Object instance1 = null;
			Object instance2 = null;
		
			instance1 = uneClasse1.newInstance();
		    instance2 = uneClasse2.newInstance();
		
		    for(Method method : uneClasse1.getDeclaredMethods()) {
		    	System.out.println(method.getName());
		    	method.invoke(instance1);
			} System.out.println("\n");
		
			for(Method method : uneClasse2.getDeclaredMethods()) {
				System.out.println(method.getName());
				method.invoke(instance2);
			} System.out.println("\n");
			
		} catch (SecurityException e) {
		    e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		} catch (ClassNotFoundException e) {
		    e.printStackTrace();
		} catch (InstantiationException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		} catch (InvocationTargetException e) {
		    e.printStackTrace();
		}
	}

}
