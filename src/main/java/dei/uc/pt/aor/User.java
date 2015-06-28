package dei.uc.pt.aor;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="user")
public class User {
	
	private Long id;
	private String email;
	private String password;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString() {
		return "Id: " + id + "\nName: " + name + "\nEmail: " + email; 
	}

}
