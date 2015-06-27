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

	public static void totalUsers() {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/totalusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.print("Total number of users: ");
			System.out.println(response.readEntity(String.class));
		}

	}

	public static void listAllUsers() {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/allusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** LIST OF ALL USERS ********\n\n");
			System.out.println(response.readEntity(UserCollection.class));
		}

	}

	public static void getUserByEmail(String email) {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/useremail");
		target = target.path(""+email);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("There is no user with email "+ email);
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** INFO OF USER ("+email+")********\n\n");
			System.out.println(response.readEntity(User.class));
		}

	}

	public static void getUserById(Long id) {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/userid");
		target = target.path(""+id);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("There is no user with id "+ id);
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** INFO OF USER ********\n\n");
			System.out.println(response.readEntity(User.class));
		}

	}

	//esta a criar user com pass 123 por defeito
	public static void createUser(String name, String email) {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/createuser");
		target = target.queryParam("name", name);
		target = target.queryParam("email", email);

		User user = new User();
		Response response = target.request().post(Entity.entity(user, "application/xml"));

		if (response.getStatus() == 204) { //acho q este é o 500
			System.out.println("An user with email "+ email +" already exists!");
		} else if (response.getStatus() == 500) {
			//confirmar
			System.out.println("Error in data base"); // ver melhor
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			//			org.postgresql.util.PSQLException (500)
		} else {
			System.out.println("******** NEW USER CREATED ********\n\n");
			System.out.println(response.readEntity(User.class));
			System.out.println("Default pass: 123");
		}

	}

	public static void deleteUser(Long id) {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/deleteuser");
		target = target.path(""+id);
		Response response = target.request().delete();

		if (response.getStatus() == 204) {
			//confirmar
			System.out.println("There is no user with id "+ id);
		} else if (response.getStatus() == 500) {
				//confirmar
				System.out.println("Error in data base."); // ver melhor
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** DELETED USER WITH "+id+" ********\n\n");
			// qq coisa com response?
		}

	}

	public static void changeUserPass(Long id, String pass) {
		System.out.println("Connecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/changepass");
		target = target.path(""+id);
		target = target.queryParam("pass", pass);

		User user = new User();
		Response response = target.request().put(Entity.entity(user, "application/xml"));

		if (response.getStatus() == 204) {
			//confirmar
			System.out.println("There is no user with id "+ id);
		} else if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			//			org.postgresql.util.PSQLException
		} else {
			System.out.println("******** UPDATED USER WITH "+id+" ********\n\n");
			System.out.println(response.readEntity(User.class));
		}

	}

}
