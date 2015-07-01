package dei.uc.pt.aor.wsconsumer;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import dei.uc.pt.aor.data.Playlist;
import dei.uc.pt.aor.data.PlaylistCollection;
import dei.uc.pt.aor.xml.TransformXML;

public class WSConsumerPlaylist {

	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/playlists";

	public static int totalPlaylists() {

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalplaylists");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean listAllPlaylists() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allplaylists");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n********************** LIST OF ALL PLAYLISTS **********************\n");
			String stringPlaylist = response.readEntity(String.class);
			System.out.println((PlaylistCollection) TransformXML.XMLToEntity(PlaylistCollection.class, stringPlaylist));
			return true;
		}

	}

	public static boolean playlistInfo(Long id) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl);
		target = target.path(""+id);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("\n* There is no playlist with id "+ id +" *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n******** INFO OF PLAYLIST ("+id+") ********\n");
			String stringPlaylist = response.readEntity(String.class);
			System.out.println((Playlist) TransformXML.XMLToEntity(Playlist.class, stringPlaylist));
			return true;
		}

	}

	public static boolean playlistsOfUser(String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/playlistsofuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n************************ PLAYLISTS OF USER ("+idemail+") ************************\n");
			String stringPlaylist = response.readEntity(String.class);
			System.out.println((PlaylistCollection) TransformXML.XMLToEntity(PlaylistCollection.class, stringPlaylist));
			return true;
		}

	}

	public static boolean updateSongPlaylist (Long sid, Long pid, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+WSpath);
		target = target.queryParam("song", sid);
		target = target.queryParam("playlist", pid);

		String xmlString = TransformXML.entityToXML(Playlist.class, new Playlist());
		Response response = target.request().put(Entity.entity(xmlString, MediaType.APPLICATION_XML));	        

		if (response.getStatus() == 500) {
			System.out.println("\n* There is no song with id "+ sid + " in playlist with id " + pid +" *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n* UPDATED PLAYLIST ("+pid+") *\n");
			return true;
		}

	}

}