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

public class WSConsumerSong {

	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/songs";	

	public static int totalSongs() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean listAllSongs() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** LIST OF ALL SONGS ********\n\n");
			System.out.println(response.readEntity(PlaylistCollection.class));
			return true;
		}

	}

	public static boolean songInfo(Long id) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl);
		target = target.path(""+id);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("There is no song with id "+ id);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** INFO OF SONG ("+id+") ********\n");
			System.out.println(response.readEntity(Song.class));
			return true;
		}

	}
	
	public static boolean deleteSong(Long id) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deletesong");
		target = target.path(""+id);
		Response response = target.request().delete();

//		if (Response.notModified())
		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** DELETED SONG WITH ID "+id+" ********\n\n");
			//ver
//			Response.notModified();
			return true;
		}

	}

	public static int totalSongsOfUser(String idemail, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongsofuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}
	
	public static boolean songsOfUser(String idemail, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/songsofuser/"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() == 204) { //500?
			System.out.println("There is no user with id/email "+ idemail);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** SONGS OF USER ("+idemail+") ********\n\n");
			System.out.println(response.readEntity(SongCollection.class));
			return true;
		}

	}

	public static int totalSongsOfPlaylist(Long pid) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongsofplaylist");
		target = target.path(""+pid);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean songsOfPlaylist(Long pid) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/songsofplaylist");
		target = target.path(""+pid);
		Response response = target.request().get();

		if (response.getStatus() == 204) { //500?
			System.out.println("There is no playlist with id "+ pid);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("******** SONGS OF PLAYLIST ("+pid+") ********\n\n");
			System.out.println(response.readEntity(SongCollection.class));
			return true;
		}

	}

}