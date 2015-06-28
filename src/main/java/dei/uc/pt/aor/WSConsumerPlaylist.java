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

public class WSConsumerPlaylist {
	
	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/playlists";

	public static void totalPlaylists() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalplaylists");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.print("Total number of playlists: ");
			System.out.println(response.readEntity(String.class));
		}

	}

	public static void listAllPlaylists() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allplaylists");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** LIST OF ALL PLAYLISTS ********\n\n");
			System.out.println(response.readEntity(PlaylistCollection.class));
		}

	}

	public static void playlistsOfUser(String idemail, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/playlistsofuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() == 204) { //500?
			System.out.println("There is no user with id/email "+ idemail);
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** PLAYLISTS OF USER ("+idemail+") ********\n\n");
			System.out.println(response.readEntity(PlaylistCollection.class));
		}

	}

	public static void updateSongPlaylist (Long sid, Long pid, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+WSpath);
		target = target.queryParam("song", sid);
		target = target.queryParam("playlist", pid);

		Playlist p = new Playlist();
		Response response = target.request().put(Entity.entity(p, "application/xml"));

		if (response.getStatus() == 500) {
			System.out.println("There is no song/playlist with id "+ sid + "/" + pid);
		} else if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** UPDATED PLAYLIST ("+pid+") ********\n\n");
//			System.out.println(response.readEntity(Playlist.class));
			//response modify??
		}

	}

}