package dei.uc.pt.aor;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

public class WSConsumerSong {

	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/songs";	

	public static int totalSongs() {
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
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** LIST OF ALL SONGS ****************\n");
			System.out.println(response.readEntity(SongCollection.class));
			return true;
		}

	}

	public static boolean songInfo(Long id) {
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
			System.out.println("\n******** INFO OF SONG ("+id+") ********\n");
			System.out.println(response.readEntity(Song.class));
			return true;
		}

	}
	
	public static boolean deleteSong(Long id) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deletesong");
		target = target.path(""+id);
		Response response = target.request().delete();

		if (response.getStatus() == 304) {
			System.out.println("There is no song with id "+ id);
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n* DELETED SONG WITH ID "+id+" *\n");
			return true;
		}

	}

	public static int totalSongsOfUser(String idemail, String WSpath) {
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
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/songsofuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();
		
		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** SONGS OF USER ("+idemail+") ****************\n");
			System.out.println(response.readEntity(SongCollection.class));
			return true;
		}

	}

	public static int totalSongsOfPlaylist(Long pid) {
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
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/songsofplaylist");
		target = target.path(""+pid);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("Failed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** SONGS OF PLAYLIST ("+pid+") ****************\n");
			System.out.println(response.readEntity(SongCollection.class));
			return true;
		}

	}

}