package edu.upr.ece.icom4035.rest;

import edu.uprm.ece.icom4035.json.Person;

public class ClientDemo {

	public static void main(String[] args) {
		try {
			PersonManager manager = new PersonManager("http://localhost:9000");
			Person P = new Person();
			P.setFirstName("Joe");
			P.setLastName("Rodriguez");
			P.setAge(18);
			
			System.out.println("Adding P#1: " + P);
		
			P =manager.storePerson(P);
			System.out.println("Added P#1: " + P);
			
			P = new Person();
			P.setFirstName("Calixto");
			P.setLastName("Smith");
			P.setAge(22);
			
			System.out.println("Adding P#2: " + P);
		
			P = manager.storePerson(P);		
			System.out.println("Added P#2: " + P);

			P = new Person();
			P.setFirstName("Renata");
			P.setLastName("Lu");
			P.setAge(17);
			
			
			System.out.println("Adding P#3: " + P);
		
			manager.storePerson(P);
			P = manager.storePerson(P);		
			System.out.println("Added P#2: " + P);
			
			System.out.println("Get P#2");
			P = manager.getPerson(2);
			System.out.println("Person #2: " + P);
			
			System.out.println("Get P#1");
			P = manager.getPerson(1);
			System.out.println("Person #1: " + P);
			
			System.out.println("Get P#11");
			P = manager.getPerson(11);
			System.out.println("Person #11: " + P);
			
			boolean status;
			System.out.println("Erase  P#1");
			status = manager.deletePerson(1);
			System.out.println("Erased: " + status);
			
			System.out.println("Erase  P#111");
			status = manager.deletePerson(111);
			System.out.println("Erased: " + status);
			
			System.out.println("Get P#1");
			P = manager.getPerson(1);
			System.out.println("Person #1: " + P);
			
			System.out.println("Get P#2");
			P = manager.getPerson(2);
			System.out.println("Person #2: " + P);
			System.out.println("Updating P# 2");
			P.setFirstName("Bernardo");
			Person P2 = manager.updatePerson(P);
			System.out.println("Updated Person #2: " + P2);

			System.out.println("Get P#2");
			P = manager.getPerson(2);
			System.out.println("Person #2: " + P);
			System.out.println("Done!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
