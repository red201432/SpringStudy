package util;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.UserDTO;

public class JwtUtil {
	private static final long   EXPIRATIONTIME=1000*60*60*24*30;
	private static final String SECRET_KEY="yourkey1";
	private static final String TOKEN_PREFIX="prefix";
	private static final String HEADER="JAuthorization";
	
	public UserDTO parseToken(String token){
		try{
			Claims body=Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token.substring(TOKEN_PREFIX.length()))
					.getBody();
			UserDTO u=new UserDTO();
			u.setUsername(body.getSubject());
	        u.setId(1L);
	        u.setRole("user");
	        return u;
		}catch (JwtException e){
			return null;
		}
	}
	
	public String generateToken(HttpServletResponse response,UserDTO user) {
		Claims claims=Jwts.claims().setSubject(user.getUsername());
		claims.put("userId", user.getId() + "");
	    claims.put("role", user.getRole());
	    String token=TOKEN_PREFIX+Jwts.builder().setClaims(claims)
					.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
					.compact();
	    response.addHeader(HEADER, token);
		return token;
	}
}
