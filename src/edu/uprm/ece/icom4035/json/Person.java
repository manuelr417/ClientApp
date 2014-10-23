package edu.uprm.ece.icom4035.json;

public class Person {
	
	private long Id;
	private String firstName;
	
	private String lastName;

	private int age;

	public Person(long id, String firstName, String lastName, int age) {
		super();
		Id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public Person(){
		
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public String toString(){
		return "(" + this.Id + ", " + this.firstName + ", " + this.lastName 
				+ ", " + this.age + ")";
	}
	
}
