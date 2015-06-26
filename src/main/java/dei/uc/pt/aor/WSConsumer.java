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
			System.out.println("2 - See user (by id)");
			System.out.println("3 - Change user name (by id)");
			System.out.println("4 - Create new user");
			System.out.println("5 - Delete user (by id)");
			System.out.println("7 - Change user pass (by id)");
			System.out.println("8 - Total users");
			System.out.println("9 - See user (by email)");
			System.out.println("0 - Quit");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				// usar log
				e.printStackTrace();
				stop = true;
				answer = "0";
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
				System.out.println("Checking user data... \n\n");
				System.out.println("Insert the user id: ");
				Long id = 0L;
				try {
					String s = reader.readLine();
					id = Long.parseLong(s);
					getUserById(id);
				} catch (Exception e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "0";
				}
				break;
			case 3: 
				System.out.println("Editing user name... \n\n");
				System.out.println("Insert the user id: ");
				id = 0L;
				try {
					String s = reader.readLine();
					//validacoes
					id = Long.parseLong(s);
					System.out.println("Insert new user name: ");
					String name = reader.readLine();
					updateUser(id, name, "");
				} catch (Exception e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "0";
				}
				break;
			case 4: 
				try {
					System.out.println("Creating new user... \n\n");
					System.out.println("Insert the name: ");
					String name = reader.readLine();
					System.out.println("Insert the email: ");
					String email = reader.readLine();
					createUser(name, email);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "0";
				}
				break;
			case 5: 
				System.out.println("Deleting user... \n\n");
				System.out.println("Insert the user id: ");
				id = 0L;
				try {
					String s = reader.readLine();
					id = Long.parseLong(s);
					//are you sure?
					deleteUser(id);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "0";
				}
				break;
			case 7: 
				System.out.println("Editing user password... \n\n");
				System.out.println("Insert the user id: ");
				id = 0L;
				try {
					String s = reader.readLine();
					id = Long.parseLong(s);
					//validacoes
					System.out.println("Insert the new pass: ");
					String pass = reader.readLine();
					// confirm pass??
					updateUser(id, "", pass);
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "0";
				}
				break;
			case 8:
				System.out.print("Total number of users: ");
				totalUsers();
				break;
			case 9:
				System.out.println("Checking user data... \n\n");
				System.out.println("Insert the user email: ");
				String email = "";
				try {
					email = reader.readLine();
					getUserByEmail(email);
				} catch (Exception e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "0";
				}
				break;
			case 0: stop = true; break;
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

	private static void totalUsers() {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/totalusers");

		Response response = target.request().get();

		System.out.println(response.readEntity(String.class));

	}

	private static void getUserByEmail(String email) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/email");
		target = target.path(""+email);

		Response response = target.request().get();

		System.out.println(response.readEntity(User.class));

	}

	private static void getUserById(Long id) {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/id");

		target = target.path(""+id);

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

	private static void deleteUser(Long id) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/deleteuser");

		target = target.path(""+id);

		System.out.println(target.getUri());

		Response response = target.request().delete();

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			// não existe email?
			//			org.postgresql.util.PSQLException
		} else System.out.println("Deleted user with id "+id); // qq coisa com response?


	}

	private static void updateUser(Long id, String name, String pass) {

		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsserver/rest/users/updateuser");

		target = target.path(""+id);  // ver se existe!!

		if (!name.equals("")) target = target.queryParam("name", name);
		if (!pass.equals("")) target = target.queryParam("pass", pass);

				System.out.println(target.getUri());

		User user = new User();
		Response response = target.request().put(Entity.entity(user, "application/xml"));

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
			// não existe email?
			//			org.postgresql.util.PSQLException
		} else System.out.println("Updated user with id "+id); // qq coisa com response?


	}

}
