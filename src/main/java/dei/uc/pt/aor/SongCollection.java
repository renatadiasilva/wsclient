package dei.uc.pt.aor;

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
		String su = String.format("%4s %13s %28s %20s \n", "ID", "NAME", "SIZE", "OWNER");
		s += su;
		s += "-------------------------------------------------------------\n";
		for (Song ss: listSongs) {
			su = String.format("%4d %-20s %-20s %-20s %10d %-30s \n", ss.getId(), ss.getTitle(),
					ss.getArtist(), ss.getAlbum(), ss.getReleaseYear(), ss.getOwner().getEmail());
			s += su;
		}
		return  s;
	}

}