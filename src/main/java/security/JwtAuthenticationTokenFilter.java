package security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import model.UserDTO;
import service.UserService;
import util.JwtUtil;
import util.LogUtil;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
			, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String token=request.getHeader("JAuthorization");
		LogUtil.error(getClass(), "Token123456 :"+token);
		if(token != null){
			UserDTO user=new JwtUtil().parseToken(token);
			LogUtil.error(getClass(), "Username :"+user.getUsername());
			if(user != null && SecurityContextHolder.getContext().getAuthentication() == null){
				UserDetails userDetails=new UserService().loadUserByUsername(user.getUsername());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        logger.info("authenticated user " + user.getUsername() + ", setting security context");
		        SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

}
