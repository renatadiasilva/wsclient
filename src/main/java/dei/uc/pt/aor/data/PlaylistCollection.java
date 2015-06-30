package dei.uc.pt.aor.data;

import java.text.SimpleDateFormat;
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
		String su = String.format("%4s %12s %14s %7s %13s \n", "ID", "NAME", "DATE", "SIZE", "OWNER");
		s += su;
		s += "-------------------------------------------------------------------\n";
		for (Playlist p: listPlaylists) {
			
			s += String.format("%4d %-20s ", p.getId(), 
					p.getName().substring(0, Math.min(p.getName().length(),20)));
			
			SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
		    String date = DATE_FORMAT.format(p.getDateOfCriation());

		    s += String.format("%s %6d %-30s \n", date, p.getSize(),
					p.getOwner().getEmail().substring(0, 
							Math.min(p.getOwner().getEmail().length(),20)));
		}
		return  s;
	}

}