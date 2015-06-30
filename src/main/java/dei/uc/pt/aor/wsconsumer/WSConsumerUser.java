package dei.uc.pt.aor.wsconsumer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
			System.out.println("\n************************ LIST OF ALL USERS ************************\n");
			String stringUser = response.readEntity(String.class);
			try {
				JAXBContext context = JAXBContext.newInstance(UserCollection.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((UserCollection) um.unmarshal(sreader));
			} catch (JAXBException e) {
				System.out.println("Error JAXB (WSConsumerUser.listAllUsers): "+ e.getMessage());
				return false;
			}
			return true;
		}

	}

	public static boolean createUser(String name, String email) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/createuser");
		target = target.queryParam("name", name);
		target = target.queryParam("email", email);

		User user = new User();
		try {
			JAXBContext context = JAXBContext.newInstance(User.class);

			BufferedWriter writer = null;
			writer = new BufferedWriter(new FileWriter("ficheiro.xml"));
			Marshaller m = context.createMarshaller();
			m.marshal(user, writer);
			String xmlString = TransformXML.convertXMLFileToString("ficheiro.xml");
			Response response = target.request().post(Entity.entity(xmlString, MediaType.APPLICATION_XML));	        

			if (response.getStatus() == 500) {
				System.out.println("An user with email "+ email +" already exists!");
				return false;
			} else if (response.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : "
						+ response.getStatus());
				return false;
			} else {
				System.out.println("\n* NEW USER CREATED *\n");
				String stringUser = response.readEntity(String.class);
				context = JAXBContext.newInstance(User.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((User) um.unmarshal(sreader));
				System.out.println("Default pass: 123");
				return true;
			}
		} catch (JAXBException e) {
			System.out.println("Error JAXB (WSConsumerUser.createUser): "+ e.getMessage());
			return false;
		} catch (IOException ioe) {
			System.out.println("Error I/O (WSConsumerUser.createUser): "+ ioe.getMessage());
			return false;
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
			String stringUser = response.readEntity(String.class);
			try {
				JAXBContext context = JAXBContext.newInstance(User.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((User) um.unmarshal(sreader));
			} catch (JAXBException e) {
				System.out.println("Error JAXB (WSConsumerUser.userInfo): "+ e.getMessage());
				return false;
			}
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
		try {
			JAXBContext context = JAXBContext.newInstance(User.class);

			BufferedWriter writer = null;
			writer = new BufferedWriter(new FileWriter("ficheiro.xml"));
			Marshaller m = context.createMarshaller();
			m.marshal(user, writer);
			String xmlString = TransformXML.convertXMLFileToString("ficheiro.xml");
			Response response = target.request().put(Entity.entity(xmlString, MediaType.APPLICATION_XML));	        

			if (response.getStatus() == 500) {
				System.out.println("There is no user with id/email "+ idemail);
				return false;
			} else if (response.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : "
						+ response.getStatus());
				return false;
			} else {
				System.out.println("\n* UPDATED USER WITH ID/EMAIL "+idemail+" *\n");
				String stringUser = response.readEntity(String.class);
				context = JAXBContext.newInstance(User.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((User) um.unmarshal(sreader));
				return true;
			}
		} catch (JAXBException e) {
			System.out.println("Error JAXB (WSConsumerUser.changeUserPass): "+ e.getMessage());
			return false;
		} catch (IOException ioe) {
			System.out.println("Error I/O (WSConsumerUser.changeUserPass): "+ ioe.getMessage());
			return false;
		}
	
	}

}