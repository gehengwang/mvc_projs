package sys.model;

import java.io.Serializable;
import java.util.List;

import tools.ClientInfo;

public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5355546037669987434L;
	private User user;
	private List<Role> roles;
	private ClientInfo clientInfo;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public ClientInfo getClientInfo() {
		return clientInfo;
	}
	public void setClientInfo(ClientInfo clientInfo) {
		this.clientInfo = clientInfo;
	}
}