package dei.uc.pt.aor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
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
			System.out.println("3 - Edit user (by email)");
			System.out.println("4 - Create new user");
			System.out.println("5 - Delete user (by email)");
			System.out.println("6 - Quit");

			try {
				answer = reader.readLine();
			} catch (IOException e) {
				// usar log
				e.printStackTrace();
				stop = true;
				answer = "6";
			}

			int op = 1000;
			try {
				op = Integer.parseInt(answer);
			} catch (Exception e) {
				op = 1000;
			}
			// try catch
			switch (op) {
			case 1: 
				listAllUsers();
				break;
			case 2: 
				System.out.println("Insert the email: ");
				String email = "";
				try {
					email = reader.readLine();
					getUserByEmail(email);  //no email
				} catch (IOException e) {
					// usar log
					e.printStackTrace();
					stop = true;
					answer = "6";
				}
				break;
			case 3: 
				System.out.println("Not implemented yet");
				break;
			case 4: 
				createUser();
				break;
			case 5: 
				System.out.println("Not implemented yet");
				break;
			case 6: stop = true; break;
			default: stop = false;
			}

		}

	}

	private static void createUser() {

		// Pode ser reutilizado !
		ResteasyClient client = new ResteasyClientBuilder().build();

		// Target também!
		ResteasyWebTarget target = client.target("http://localhost:8080/thews-ws/rest/simpleusers/simpleuser");


		User theuser = new User();
		theuser.setName("outro");


		// request e response já terão de ser criados por request e response
		Response response = target.request(MediaType.APPLICATION_XML).post(
				Entity.entity(theuser, "application/xml"));

		if (response.getStatus() != 200) { // se nao correu tudo bem !
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		// 1# em objecto ! 
		//SimpleUser usr_do_server =  response.readEntity(SimpleUser.class);
		//System.out.println(usr_do_server.getUsername());
		// ou um ou outro.. maneira 2# em string .. xml (de notar que a resposta tem o atributo id.. mas como o nosso objecto não o usa.. não o carrega)
		System.out.println(response.readEntity(String.class));


	}

	private static void listAllUsers() {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsclient/rest/users/allusers");

		Response response = target.request().get();

		System.out.println(response.readEntity(Users.class));

	}

	private static void getUserByEmail(String email) {
		ResteasyClient client = new ResteasyClientBuilder().build();

		ResteasyWebTarget target = client.target("http://localhost:8080/playlist-wsclient/rest/users");

		// importante ! alterações na path nao tomam efeito, temos sempre de mudar a referencia
		// target.path(""+id);  // no effect.. 
		target = target.path(""+email);// correct !

		// só para ver se está :)
//		System.out.println(target.getUri());

		// default será o primeiro MediaType do service 
		Response response = target.request().get();

//		System.out.println(response.readEntity(String.class));
//
		System.out.println(response.readEntity(User.class));
		
		// if null n existe
//		if (response.getStatus() != 200) { // se nao correu tudo bem !
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ response.getStatus());
//		}

	}

}
