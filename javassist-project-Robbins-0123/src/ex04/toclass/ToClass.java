package ex04.toclass;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.NotFoundException;
import target.Common;

public class ToClass {
	
	private static final String PKG_NAME = "target" + ".";
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String tarClass = "";
		
		// Get class name from user, return if multiple classes are given.
		do {
			System.out.println("=================================================");
		    System.out.println("EX04 - Please enter one class name to be modified");
		    System.out.println("EX04 - Enter q to terminate the program.         ");
		    System.out.println("=================================================");
		    
		    String[] inputs = util.UtilOption.getInputs();
		    
		    if(inputs.length != 1) {
		    	System.out.println("[WRN] Invalid Input");
		    } else {
		    	tarClass = inputs[0];
		    }
		} while(tarClass.compareTo("") == 0);
		
		String classname = PKG_NAME + tarClass;
		
		// Modify given class
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(classname);
			
			// Insert print block
			CtConstructor declaredConstructor = cc.getDeclaredConstructor(new CtClass[0]);
			String printBlock = "{ " +
					"System.out.println(\"id: \" + id);" +
					"System.out.println(\"year: \" + year);" +
					" }";
			declaredConstructor.insertAfter(printBlock);
			
			Class<?> c = cc.toClass();
			
			// Should print id and year values on instantiation
			Common ci = (Common) c.newInstance();
			
		} catch (NotFoundException | CannotCompileException | 
				InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
	    }
	}
}
