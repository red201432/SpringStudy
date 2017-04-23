package security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private String token;
	
	public JwtAuthenticationToken(String token) {
		// TODO Auto-generated constructor stub
		super(null, null);
		this.token=token;
	}
	

	public String getToken() {
		return token;
	}

	@Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

	
}
