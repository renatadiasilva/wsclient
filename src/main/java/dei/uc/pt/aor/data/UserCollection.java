package dei.uc.pt.aor.data;

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
			su = String.format("%4d  %-25s %-30s \n", u.getId(), 
					u.getName().substring(0, Math.min(u.getName().length(), 20)),
					u.getEmail().substring(0, Math.min(u.getEmail().length(), 20)));
			s += su;
		}
		return  s;
	}

}