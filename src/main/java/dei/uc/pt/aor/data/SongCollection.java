package dei.uc.pt.aor.data;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class SongCollection {
	
	@XmlElement(name="song")
	private List<Song> listSongs;
	
	public List<Song> getList() {
		return listSongs;
	}

	public void setList(List<Song> list) {
		this.listSongs = list;
	}
	
	public String toString() {
		String s = "";
		if ( (listSongs == null) || (listSongs.size() == 0) ) return "No songs";
		String su = String.format("%4s %12s %22s %22s %14s \n", "ID", "TITLE",
				"ARTIST", "ALBUM", "YEAR");
		s += su;
		s += "--------------------------------------------------------------------------------\n";
		for (Song ss: listSongs) {
			su = String.format("%4d %-20s %-20s %-20s %10d \n", ss.getId(), 
					ss.getTitle().substring(0, Math.min(ss.getTitle().length(),20)),
					ss.getArtist().substring(0, Math.min(ss.getArtist().length(),20)),
					ss.getAlbum().substring(0, Math.min(ss.getAlbum().length(),20)),
					ss.getReleaseYear());
			s += su;
		}
		return  s;
	}

}