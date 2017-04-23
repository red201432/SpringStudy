package service;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import util.RSAUtil;

/*
 * 自定义登录验证
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserService userService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username=authentication.getName();
		String password=(String)authentication.getCredentials();
		UserDetails user = (UserDetails) userService.loadUserByUsername(username);
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }
        try {
			if(user.getPassword().equals(password)){
				Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		        return new UsernamePasswordAuthenticationToken(user, password, authorities);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
