package configuration;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import util.LogUtil;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint,Serializable {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		LogUtil.error(getClass(), request.getRequestURI());
		response.sendRedirect("/login?url="+request.getRequestURI());
	}
}
