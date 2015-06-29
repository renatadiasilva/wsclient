package dei.uc.pt.aor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="song")
public class Song {
	
	private Long id;
	private String title;
	private String artist;
	private String album;
	private int releaseYear;
	private String pathFile;
	private User owner;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String path) {
		this.pathFile = path;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public String toString() {
		String s = String.format("%6s: %d \n", "Id", id);
		s += String.format("%6s: %s \n", "Title", title);
		s += String.format("%6s: %s \n", "Artist", artist);
		s += String.format("%6s: %s \n", "Album", album);
		s += String.format("%6s: %d \n", "Year", releaseYear);
		s += String.format("%6s: %s \n", "Path", pathFile);
		s += String.format("%6s: %s \n", "Owner", owner.getEmail());
		return s;
	}
	
}
