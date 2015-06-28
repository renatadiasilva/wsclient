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

	public static void totalSongs() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.print("Total number of songs: ");
			System.out.println(response.readEntity(String.class));
		}

	}

	public static void listAllSongs() {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** LIST OF ALL SONGS ********\n\n");
			System.out.println(response.readEntity(PlaylistCollection.class));
		}

	}

	public static void songInfo(Long id) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl);
		target = target.path(""+id);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("There is no song with id "+ id);
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** INFO OF SONG ("+id+") ********\n");
			System.out.println(response.readEntity(Song.class));
		}

	}
	
	public static void deleteSong(Long id) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deletesong");
		target = target.path(""+id);
		Response response = target.request().delete();

//		if (Response.notModified())
		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** DELETED SONG WITH ID "+id+" ********\n\n");
			//ver
//			Response.notModified();
		}

	}

	public static void songsOfUser(String idemail, String WSpath) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/songsofuser/"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() == 204) { //500?
			System.out.println("There is no user with id/email "+ idemail);
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** SONGS OF USER ("+idemail+") ********\n\n");
			System.out.println(response.readEntity(SongCollection.class));
		}

	}

	public static void songsOfPlaylist(Long pid) {
		System.out.println("\nConnecting...\n");
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/songsofplaylist");
		target = target.path(""+pid);
		Response response = target.request().get();

		if (response.getStatus() == 204) { //500?
			System.out.println("There is no playlist with id "+ pid);
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
		} else {
			System.out.println("******** SONGS OF PLAYLIST ("+pid+") ********\n\n");
			System.out.println(response.readEntity(SongCollection.class));
		}

	}

}