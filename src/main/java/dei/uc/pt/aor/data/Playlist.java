package dei.uc.pt.aor.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="playlist")
public class Playlist {

	private Long id;
	private String name;
	private Date dateOfCriation;
	private int size;
	private User owner;
	
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

	public String toString() {
		String s = String.format("%5s: %d \n", "Id", id);
		s += String.format("%5s: %s \n", "Name", name);
		s += String.format("%5s: %s \n", "Date", dateOfCriation.toString());
		s += String.format("%5s: %s \n", "Size", size);
		s += String.format("%5s: %s \n", "Owner", owner.getEmail());
		return s;
	}

}