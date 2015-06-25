package dei.uc.pt.aor;

//import java.util.List;

public class Song {
	
	private Long id;
	private String title;
	private String artist;
	private String album;
	private int releaseYear;
	private String pathFile;
	private User owner;
	
//	private List<Playlist> playlists;

	public Song() {
	}

	public Song(String title, String artist, String album, int releaseYear,
			String path, User owner) {
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.releaseYear = releaseYear;
		this.pathFile = path;
		this.owner = owner;
	}

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
		return title + " -> " + artist + ", " + album + ", " + releaseYear
				+ ", " + pathFile + owner.getName();
	}
	
}
