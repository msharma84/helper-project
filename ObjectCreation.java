package com;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ObjectCreation implements Cloneable {
	
	// Constructors 
	public ObjectCreation() {
		//System.out.println("Default Constructor ->");
	}
	
	public ObjectCreation(String s) {
		System.out.println("Parametrized Constructor ->"+s);
	}

	public static void main(String[] args) {
		
		// TYPE 1 :
		// Create object using the new keyword
		ObjectCreation obj1 = new ObjectCreation();
		obj1.print();
		obj1.printHashCode(obj1);
		
		
		
		// TYPE 2 :
		// Create object using the Class.forName()
		ObjectCreation obj2 = null;
		try {
			Class<ObjectCreation> claz = (Class<ObjectCreation>) Class.forName("scan.ObjectCreation");
			// The method newInstance() from the type Class<ObjectCreation> is deprecated since version 9
			obj2 = claz.newInstance();
			obj2.print();
			obj2.printHashCode(obj2);
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found Exception Message - "+e.getMessage());
		} catch (InstantiationException e) {
			System.err.println("Instantiation Exception Message - "+e.getMessage());
		} catch (IllegalAccessException e) {
			System.err.println("Illegal Access Exception Message - "+e.getMessage());
		}
		
		if(obj2!= null) {
			obj2.print();
			obj2.printHashCode(obj2);
		}
		
		
		
		// TYPE 3 :
		// Create object through cloning
		// Cloneable interface should be implemented by the class whose objects need to be cloned.
		ObjectCreation obj3 = null;
		try {
			obj3 = (ObjectCreation) obj1.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Clone Not Supported Exception Message - "+e.getMessage());
		}
		if(obj3!= null) {
			obj3.print();
			obj3.printHashCode(obj3);
		}
		
		
		
		// TYPE 4 :
		// Creating object using newInstance()
		ObjectCreation obj4 = null;
		try {
			obj4 = (ObjectCreation) ObjectCreation.class.getClassLoader().loadClass("scan.ObjectCreation").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.err.println("Exception with new Instance Message - "+e.getMessage());
		}
		if(obj4!= null) {
			obj4.print();
			obj4.printHashCode(obj4);
		}
		
		
		
		// TYPE 5 :
		// Creating object with Class instance
		Class clazz = ObjectCreation.class;
		Constructor<ObjectCreation> constructor;
		ObjectCreation obj5 = null;
		try {
			
			// Calling default constructor
			constructor = clazz.getDeclaredConstructor();
			obj4 = constructor.newInstance();
			
			// Calling parameterized constructor
			//constructor = clazz.getDeclaredConstructor(new Class[] {String.class});
			//obj4 = constructor.newInstance(new Object[]{"Object"});
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Exception with Class Instance Message - "+e.getMessage());
		}
		if(obj5!= null) {
			obj5.print();
			obj5.printHashCode(obj5);
		}
		
		
		
		// TYPE 6 :
		// Creating class using the Serialization
		ObjectCreation obj6 = null;
		try {
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("serialize.ser"));
			obj6 = (ObjectCreation) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Exception with Serialization Exception Message - "+e.getMessage());
		}
		if(obj6!= null) {
			obj6.print();
			obj6.printHashCode(obj6);
		}
		
		
	}
	
	public void print() {
		System.out.println("*Superb* Object has been created !");
	}

	public void printHashCode(Object obj) {
		System.out.println("Hash code - "+obj.hashCode());
	}
}

