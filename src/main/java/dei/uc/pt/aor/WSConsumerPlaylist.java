package dei.uc.pt.aor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

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
			System.out.println(response.readEntity(PlaylistCollection.class));
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
			System.out.println(response.readEntity(Playlist.class));			
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
			System.out.println(response.readEntity(PlaylistCollection.class));
			return true;
		}

	}

	public static boolean updateSongPlaylist (Long sid, Long pid, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+WSpath);
		target = target.queryParam("song", sid);
		target = target.queryParam("playlist", pid);

		Playlist p = new Playlist();
		Response response = target.request().put(Entity.entity(p, "application/xml"));

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

	}

}