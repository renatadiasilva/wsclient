package dei.uc.pt.aor;

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

public class WSConsumerPlaylist {

	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/playlists";

	public static int totalPlaylists() {

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalplaylists");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean listAllPlaylists() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allplaylists");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** LIST OF ALL PLAYLISTS ****************\n");
			String stringUser = response.readEntity(String.class);
			try {
				JAXBContext context = JAXBContext.newInstance(PlaylistCollection.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((PlaylistCollection) um.unmarshal(sreader));
			} catch (JAXBException e) {
				System.out.println("Error JAXB (WSConsumerPlaylist.listAllPlaylist): "+ e.getMessage());
				return false;
			}
			return true;
		}

	}

	public static boolean playlistInfo(Long id) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl);
		target = target.path(""+id);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("There is no playlist with id "+ id);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n******** INFO OF PLAYLIST ("+id+") ********\n");
			String stringUser = response.readEntity(String.class);
			try {
				JAXBContext context = JAXBContext.newInstance(Playlist.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((Playlist) um.unmarshal(sreader));
			} catch (JAXBException e) {
				System.out.println("Error JAXB (WSConsumerPlaylist.playlistInfo): "+ e.getMessage());
				return false;
			}
			return true;
		}

	}

	public static boolean playlistsOfUser(String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/playlistsofuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** PLAYLISTS OF USER ("+idemail+") ****************\n");
			String stringUser = response.readEntity(String.class);
			try {
				JAXBContext context = JAXBContext.newInstance(PlaylistCollection.class);
				Unmarshaller um = context.createUnmarshaller();
				StringReader sreader = new StringReader(stringUser);
				System.out.println((PlaylistCollection) um.unmarshal(sreader));
			} catch (JAXBException e) {
				System.out.println("Error JAXB (WSConsumerPlaylist.playlistOfUser): "+ e.getMessage());
				return false;
			}
			return true;
		}

	}

	public static boolean updateSongPlaylist (Long sid, Long pid, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+WSpath);
		target = target.queryParam("song", sid);
		target = target.queryParam("playlist", pid);

		Playlist p = new Playlist();
		try {
			JAXBContext context = JAXBContext.newInstance(User.class);

			BufferedWriter writer = null;
			writer = new BufferedWriter(new FileWriter("ficheiro.xml"));
			Marshaller m = context.createMarshaller();
			m.marshal(p, writer);
			String xmlString = TransformXML.convertXMLFileToString("ficheiro.xml");
			Response response = target.request().put(Entity.entity(xmlString, MediaType.APPLICATION_XML));	        

			if (response.getStatus() == 500) {
				System.out.println("There is no song with id "+ sid + " in playlist with id " + pid);
				return false;
			} else if (response.getStatus() != 200) {
				System.out.println("Failed : HTTP error code : "
						+ response.getStatus());
				return false;
			} else {
				System.out.println("\n* UPDATED PLAYLIST ("+pid+") *\n");
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