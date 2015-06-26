package dei.uc.pt.aor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class WSConsumer {

	public static void main(String[] args) {

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in));

		boolean stop = false;
		String answer = "1";  // ver

		while (!stop) {

			System.out.println("\n\n***** Choose *****");
			System.out.println("1 - List users ");
			System.out.println("2 - See user (by email)");
			System.out.println("3 - Edit user name (by email)");
			System.out.println("4 - Create new user");
			System.out.println("5 - Delete user (by email)");
			System.out.println("6 - Quit");
			System.out.println("7 - Edit user pass (by email)");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				// usar log
				e.printStackTrace();
				stop = true;
				answer = "6";
			}
			
			System.out.println("\n");

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}
			// try catch
			switch (op) {
			case 1: 
				System.out.println("Listing all users...\n\n");
				listAllUsers();
				break;
			case 2: 
				System.out.println("Insert the email: ");
				String email = "";
				try {
					email = reader.readLine();
					getUserByEmail(email);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "6";
				}
				break;
			case 3: 
				try {
					System.out.println("Insert the email: ");
					email = reader.readLine();
					//validacoes
					System.out.println("Insert new user name: ");
					String name = reader.readLine();
					updateUser(email, name, "");
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "6";
				}
				break;
			case 4: 
				try {
					System.out.println("Insert the name: ");
					String name = reader.readLine();
					System.out.println("Insert the email: ");
					email = reader.readLine();
					createUser(name, email);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "6";
				}
				break;
			case 5: 
				System.out.println("Insert the email: ");
				email = "";
				try {
					email = reader.readLine();
					//are you sure?
					deleteUser(email);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "6";
				}
				break;
			case 6: stop = true; break;
			case 7: 
				try {
					System.out.println("Insert the email: ");
					email = reader.readLine();
					//validacoes
					System.out.println("Insert the new pass: ");
					String pass = reader.readLine();
					// confirm pass??
					updateUser(email, "", pass);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "6";
				}
				break;
			default: stop = false;
			}

		}

	}

	private static void listAllUsers() {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/allusers");

		Response response = target.request().get();

		System.out.println(response.readEntity(Users.class));

	}

	private static void getUserByEmail(String email) {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users");

		target = target.path(""+email);

		// só para ver se está :)
//		System.out.println(target.getUri());

		Response response = target.request().get();

		System.out.println(response.readEntity(User.class));
		
		// if null sysout n existe

//		if (response.getStatus() != 200) { // se nao correu tudo bem !
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ response.getStatus());
//		}

	}

	//esta a criar user com pass 123 por defeito
	private static void createUser(String name, String email) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/createuser");

		target = target.queryParam("name", name);
		target = target.queryParam("email", email);

//		System.out.println(target.getUri());

		User user = new User();

		// request e response já terão de ser criados por request e response
		Response response = target.request().post(Entity.entity(user, "application/xml"));

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			// já existe email?
//			org.postgresql.util.PSQLException
		} else System.out.println(response.readEntity(User.class));


	}

	private static void deleteUser(String email) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/deleteuser");

		target = target.path(""+email);

		System.out.println(target.getUri());

		Response response = target.request().delete();

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			// não existe email?
//			org.postgresql.util.PSQLException
		} else System.out.println("Deleted user with email "+email); // qq coisa com response?


	}
	
	private static void updateUser(String email, String name, String pass) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/updateuser");

		target = target.path(""+email);  // ver se existe!!
		
		if (!name.equals("")) target = target.queryParam("name", name);
		if (!pass.equals("")) target = target.queryParam("pass", pass);

//		System.out.println(target.getUri());

		User user = new User();
		Response response = target.request().put(Entity.entity(user, "application/xml"));

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			// não existe email?
//			org.postgresql.util.PSQLException
		} else System.out.println("Updated user with email "+email); // qq coisa com response?


	}
	
	
	//total users
	//http://localhost:8080/playlist-wsserver/rest/users/totalusers
	
	

}
