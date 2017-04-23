package model;

public class UserDTO {
	private Long id;
	private String username;
	private String role;
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getRole() {
		return role;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
