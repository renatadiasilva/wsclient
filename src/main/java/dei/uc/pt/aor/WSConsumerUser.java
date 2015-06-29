package dei.uc.pt.aor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class WSConsumerUser {
	
	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/users";	

	public static int totalUsers() {

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
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** LIST OF ALL USERS ****************\n");
			System.out.println(response.readEntity(UserCollection.class));
			return true;
		}

	}

	public static boolean createUser(String name, String email) {
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
			System.out.println("\n* NEW USER CREATED *\n");
			System.out.println(response.readEntity(User.class));
			System.out.println("Default pass: 123");
			return true;
		}

	}

	public static boolean userInfo(String idemail, String WSpath) {
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
			System.out.println("\n******** INFO OF USER ("+idemail+") ********");
			System.out.println(response.readEntity(User.class));
			return true;
		}

	}

	public static boolean deleteUser(String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deleteuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().delete();

		if (response.getStatus() == 304) {
			System.out.println("There is no user with id/email "+ idemail);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n* DELETED USER WITH ID/EMAIL "+idemail+" *\n");
			return true;
		}

	}

	public static boolean changeUserPass(String idemail, String pass, String WSpath) {
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
			System.out.println("\n* UPDATED USER WITH ID/EMAIL "+idemail+" *\n");
			System.out.println(response.readEntity(User.class));
			return true;
		}

	}

}