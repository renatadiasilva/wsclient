package dei.uc.pt.aor;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class Users {
	
	@XmlElement(name="user")
	private List<User> listUsers;
	
	public List<User> getList() {
		return listUsers;
	}

	public void setList(List<User> list) {
		this.listUsers = list;
	}
	
	public String toString() {
		String s = "";
		if ( (listUsers == null) || (listUsers.size() == 0) ) return "No users";
		for (User u: listUsers) s += u+"\n";
		return  s;
	}

}
