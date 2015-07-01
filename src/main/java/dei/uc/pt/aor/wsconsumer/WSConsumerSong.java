package dei.uc.pt.aor.wsconsumer;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import dei.uc.pt.aor.data.Song;
import dei.uc.pt.aor.data.SongCollection;
import dei.uc.pt.aor.xml.TransformXML;

public class WSConsumerSong {

	final static private String WSurl = "http://localhost:8080/playlist-wsserver/rest/songs";	

	public static int totalSongs() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return -1;
		} else return Integer.parseInt(response.readEntity(String.class));

	}

	public static boolean listAllSongs() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/allsongs");
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n************************ LIST OF ALL SONGS ************************\n");
			String stringSong = response.readEntity(String.class);
			System.out.println((SongCollection) TransformXML.XMLToEntity(SongCollection.class, stringSong));
			return true;
		}

	}

	public static boolean songInfo(Long id) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl);
		target = target.path(""+id);
		Response response = target.request().get();

		if (response.getStatus() == 204) {
			System.out.println("\n* There is no song with id "+ id +" *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n******** INFO OF SONG ("+id+") ********\n");
			String stringSong = response.readEntity(String.class);
			System.out.println((Song) TransformXML.XMLToEntity(Song.class, stringSong));
			return true;
		}

	}
	
	public static boolean deleteSong(Long sid, String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/deletesongofuser"+WSpath);
		target = target.queryParam("sid", sid);
		target = target.queryParam("uidemail", idemail);
		Response response = target.request().delete();
		
		if (response.getStatus() == 304) {
			System.out.println("\n* There is no song with id "+ sid + " added by user with "
					+ "id/email "+idemail +" *");
			return false;
		} else if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n* THE SONG WITH ID "+sid+" now belongs to ADMIN *\n");
			return true;
		}

	}

	public static int totalSongsOfUser(String idemail, String WSpath) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongsofuser"+WSpath);
		target = target.path(idemail);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
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
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** SONGS OF USER ("+idemail+") ****************\n");
			String stringSong = response.readEntity(String.class);
			System.out.println((SongCollection) TransformXML.XMLToEntity(SongCollection.class, stringSong));
			return true;
		}

	}

	public static int totalSongsOfPlaylist(Long pid) {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(WSurl+"/totalsongsofplaylist");
		target = target.path(""+pid);
		Response response = target.request().get();

		if (response.getStatus() != 200) {
			System.out.println("\nFailed : HTTP error code : "
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
			System.out.println("\nFailed : HTTP error code : "
					+ response.getStatus());
			return false;
		} else {
			System.out.println("\n**************** SONGS OF PLAYLIST ("+pid+") ****************\n");
			String stringSong = response.readEntity(String.class);
			System.out.println((SongCollection) TransformXML.XMLToEntity(SongCollection.class, stringSong));
			return true;
		}

	}

}