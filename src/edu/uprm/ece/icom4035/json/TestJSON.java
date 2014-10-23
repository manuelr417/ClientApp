package edu.uprm.ece.icom4035.json;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJSON {
	
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		Person bob = new Person(12, "Bob", "Diaz", 21);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
		String jsonString = mapper.writeValueAsString(bob);
		System.out.println("POJO: " + jsonString);
		
		Person P = mapper.readValue(jsonString, Person.class);
		System.out.println("Person: " + P);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		

	}

}
