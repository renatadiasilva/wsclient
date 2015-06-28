package dei.uc.pt.aor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Playlist {

	private Long id;
	private String name;
	private Date dateOfCriation;
	private int size;
	private User owner;
	
	private List<Song> songs;
	
	public Playlist() {
	}
	
	public Playlist(String name, Date dateOfCriation, User owner) {
		this.name = name;
		this.dateOfCriation = dateOfCriation;
		this.owner = owner;
		this.size = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfCriation() {
		return dateOfCriation;
	}

	public void setDateOfCriation(Date dateOfCriation) {
		this.dateOfCriation = dateOfCriation;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	public User getOwner() {
		return owner;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Song> songs) {
		this.songs = songs;
	}
	
	public String toString() {
		return "Id: " + id + "\nName: " + name + "\nDate of Creation: " + dateOfCriation 
				+ "\nOwner: " + owner.getName() + "\nSize: " + size;
		
	}

}
