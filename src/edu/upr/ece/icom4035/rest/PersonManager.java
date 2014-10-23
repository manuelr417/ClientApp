package edu.upr.ece.icom4035.rest;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uprm.ece.icom4035.json.Person;

public class PersonManager {
	private String serverUrl;
	private String personUrl;
	private CloseableHttpClient httpclient;
	private CloseableHttpResponse response;
	private ObjectMapper mapper;
	
	public PersonManager(String serverUrl){
		if (serverUrl == null){
			throw new IllegalArgumentException("Server url cannot be null.");
		}
		
		this.serverUrl = serverUrl;
		this.personUrl = this.serverUrl + "/person";
		this.httpclient = HttpClients.createDefault();
		this.mapper = new ObjectMapper();
	}

	// Get a Person based on Id.
	// Exceptions deal with server errors (IOException) or malformed HTTP client protocol evaluation
	// (ClientProtocolException)
	public Person getPerson(int id) throws ClientProtocolException, IOException{
		// Init the client
		//this.httpclient = HttpClients.createDefault();

		// Set up route to get person with a given id
		String getRoute = this.personUrl + "/" + id;
		// Set up http get operation on the given route
		HttpGet httpget = new HttpGet(getRoute);
		// Execute get operation
		CloseableHttpResponse response = this.httpclient.execute(httpget);
		Person P = null;
		// get the status code
		int statusCode = response.getStatusLine().getStatusCode();
		// code 200 : OK - found it!
		if (statusCode == HttpStatus.SC_OK){
			

			P = readPerson(response);	
		}
		// code 404 : Not found
		else if (statusCode == HttpStatus.SC_NOT_FOUND){
			P = null;
		}
		else {
			// In android client app, simply show an error message. Do not kill the app!
			throw new IllegalStateException("Something is wrong with server.");
		}
		// Close response
		response.close();
		//this.httpclient.close();
		return P;
	}

	private Person readPerson(CloseableHttpResponse response)
			throws IOException, JsonProcessingException, JsonParseException,
			JsonMappingException {
		Person P;

		// Read the response from server and convert to String. This will help parse JSON data.
		HttpEntity entity = response.getEntity();
		String line = EntityUtils.toString(entity, "UTF-8");

		// DEBUG line - just to look at what server sent. Not to be used in actual app
		System.out.println("Data Read: " + line);

		// Convert String to JSON Tree
		JsonNode data = mapper.readTree(line);
		// Find the person data under the tag Person
		JsonNode person = data.with("Person");
		// Convert again to JSON string
		String personStr = person.toString();
		// Use the mapper to convert JSON string into Person Java Plain Old Object (POJO)
		P = mapper.readValue(personStr, Person.class);
		return P;
	}
	
	public Person storePerson(Person newPerson) throws ClientProtocolException, IOException{
		// Convert the Person to JSON String
		String newstring = mapper.writeValueAsString(newPerson);
		// Build a Request to server
		StringEntity requestEntity = new StringEntity(newstring, ContentType.APPLICATION_JSON);
		// Build URL
		String url = this.personUrl;
		// Build POST message since this is a new object
		HttpPost postMethod = new HttpPost(url);
		// Set the payload entity for the post
		postMethod.setEntity(requestEntity);
		// Execute the Post
		response = httpclient.execute(postMethod);
		// Get the status code
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_CREATED){
			Person P = readPerson(response);
			response.close();
			return P;
		}
		else{
			response.close();
			return null;
		}
	}

	public boolean deletePerson(int id) throws ClientProtocolException, IOException{

		// Set up route to get person with a given id
		String getRoute = this.personUrl + "/" + id;
		// Set up http delete operation on the given route
		HttpDelete httpdelete = new HttpDelete(getRoute);
		// Execute delete operation
		CloseableHttpResponse response = this.httpclient.execute(httpdelete);
		boolean result;
		// get the status code
		int statusCode = response.getStatusLine().getStatusCode();
		// code 200 : OK - found it!
		if (statusCode == HttpStatus.SC_NO_CONTENT){
			result=	true;		
		}
		// code 404 : Not found
		else if (statusCode == HttpStatus.SC_NOT_FOUND){
			result= false;
		}
		else {
			// In android client app, simply show an error message. Do not kill the app!
			throw new IllegalStateException("Something is wrong with server.");
		}
		// Close response
		response.close();

		return result;
	}

	public Person updatePerson(Person newPerson) throws ClientProtocolException, IOException{
		// Convert the Person to JSON String
		String newstring = mapper.writeValueAsString(newPerson);
		// Build a Request to server
		StringEntity requestEntity = new StringEntity(newstring, ContentType.APPLICATION_JSON);
		// Build URL
		String url = this.personUrl + "/"+ newPerson.getId();
		// Build PUT message since this is a new object
		HttpPut putMethod = new HttpPut(url);
		
		// Set the payload entity for the put
		putMethod.setEntity(requestEntity);
		// Execute the Post
		response = httpclient.execute(putMethod);
		// Get the status code
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == HttpStatus.SC_OK){
			Person P = readPerson(response);
			response.close();
			return P;
		}
		else{
			response.close();
			return null;
		}
	}	
}
