package dei.uc.pt.aor.wsconsumer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import dei.uc.pt.aor.data.User;
import dei.uc.pt.aor.data.UserCollection;
import dei.uc.pt.aor.xml.TransformXML;

public class WSConsumerUser {

	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/users";	

	public static int totalUsers() {

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static int totalLoggedUsers() {

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalloggedusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean listAllUsers() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n************************ LIST OF ALL USERS ************************\n");
			String stringUser = response.readEntity(String.class);
			System.out.println((UserCollection) TransformXML.XMLToEntity(UserCollection.class, stringUser));
			return true;
		}

	}

	public static boolean listAllLoggedUsers() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/loggedusers");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n************************ LIST OF LOGGED USERS ************************\n");
			String stringUser = response.readEntity(String.class);
			System.out.println((UserCollection) TransformXML.XMLToEntity(UserCollection.class, stringUser));
			return true;
		}

	}

	public static boolean createUser(String name, String email) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/createuser");
		target = target.queryParam("name", name);
		target = target.queryParam("email", email);

		String xmlString = TransformXML.entityToXML(User.class, new User());
		Response response = target.request().post(Entity.entity(xmlString, MediaType.APPLICATION_XML));	        

		if (response.getStatus() == 598) {
			System.out.println("\n* An user with email "+ email +" already exists *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n* NEW USER CREATED *\n");
			String stringUser = response.readEntity(String.class);
			System.out.println((User) TransformXML.XMLToEntity(User.class, stringUser));
			System.out.println("(Default pass: 123)");
			return true;
		}

	}

	public static boolean userInfo(String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("\n* There is no user with id/email "+ idemail +" *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n******** INFO OF USER ("+idemail+") ********");
			String stringUser = response.readEntity(String.class);
			System.out.println((User) TransformXML.XMLToEntity(User.class, stringUser));
			return true;
		}

	}

	public static boolean deleteUser(String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deleteuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().delete();

		if (response.getStatus() == 304) {
			System.out.println("\n* There is no user with id/email "+ idemail +" *");
			return false;
		} else if (response.getStatus() == 598) {
				System.out.println("\n* Removal not allowed: the user with id/email "+ idemail +" is logged *");
				return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
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

		String xmlString = TransformXML.entityToXML(User.class, new User());
		Response response = target.request().put(Entity.entity(xmlString, MediaType.APPLICATION_XML));	        

		if (response.getStatus() == 598) {
			System.out.println("\n* There is no user with id/email "+ idemail +" *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n* UPDATED USER WITH ID/EMAIL "+idemail+" *\n");
			String stringUser = response.readEntity(String.class);
			System.out.println((User) TransformXML.XMLToEntity(User.class, stringUser));
			return true;
		}

	}

}