package dei.uc.pt.aor;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class PlaylistCollection {
	
	@XmlElement(name="playlist")
	private List<Playlist> listPlaylists;
	
	public List<Playlist> getList() {
		return listPlaylists;
	}

	public void setList(List<Playlist> list) {
		this.listPlaylists = list;
	}
	
	public String toString() {
		String s = "";
		if ( (listPlaylists == null) || (listPlaylists.size() == 0) ) return "No playlists";
		String su = String.format("%4s %13s %28s %20s \n", "ID", "NAME", "SIZE", "OWNER");
		s += su;
		s += "-------------------------------------------------------------\n";
		for (Playlist p: listPlaylists) {
			su = String.format("%4d  %-25s %10d %-30s \n", p.getId(), p.getName(),
					p.getSize(), p.getOwner().getEmail());
			s += su;
		}
		return  s;
	}

}