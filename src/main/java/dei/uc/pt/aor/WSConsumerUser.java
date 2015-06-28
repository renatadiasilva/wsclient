package dei.uc.pt.aor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class WSConsumerUser {
	
	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/users";	

	public static int totalUsers() {

		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean listAllUsers() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** LIST OF ALL USERS ********\n\n");
			System.out.println(response.readEntity(UserCollection.class));
			return true;
		}

	}

	public static boolean createUser(String name, String email) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/createuser");
		target = target.queryParam("name", name);
		target = target.queryParam("email", email);

		User user = new User();
		Response response = target.request().post(Entity.entity(user, "application/xml"));

		if (response.getStatus() == 500) {
			System.out.println("An user with email "+ email +" already exists!");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** NEW USER CREATED ********\n\n");
			System.out.println(response.readEntity(User.class));
			System.out.println("Default pass: 123");
			return true;
		}

	}

	public static boolean userInfo(String idemail, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("There is no user with id/email "+ idemail);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** INFO OF USER ("+idemail+") ********\n");
			System.out.println(response.readEntity(User.class));
			return true;
		}

	}

	public static boolean deleteUser(String idemail, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deleteuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().delete();

//		if (Response.notModified())
		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** DELETED USER WITH ID/EMAIL "+idemail+" ********\n\n");
			//ver
//			Response.notModified();
			return true;
		}

	}

	public static boolean changeUserPass(String idemail, String pass, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/changepass"+WSpath);
		target = target.path(idemail);
		target = target.queryParam("pass", pass);

		User user = new User();
		Response response = target.request().put(Entity.entity(user, "application/xml"));

		if (response.getStatus() == 500) {
			System.out.println("There is no user with id/email "+ idemail);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** UPDATED USER WITH ID/EMAIL "+idemail+" ********\n\n");
			System.out.println(response.readEntity(User.class));
			return true;
		}

	}

}