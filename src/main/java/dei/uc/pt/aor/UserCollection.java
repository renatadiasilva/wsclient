package dei.uc.pt.aor;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="collection")
public class UserCollection {
	
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
		String su = String.format("%4s %13s %28s\n", "ID", "NAME", "EMAIL");
		s += su;
		s += "-------------------------------------------------------------\n";
		for (User u: listUsers) {
			su = String.format("%4d  %-25s %-30s \n", u.getId(), u.getName(), u.getEmail());
			s += su;
		}
		return  s;
	}

}