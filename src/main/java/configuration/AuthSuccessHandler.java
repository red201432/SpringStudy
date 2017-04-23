package configuration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import model.UserDTO;
import util.CookieUtil;
import util.JwtUtil;
import util.LogUtil;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, 
		HttpServletResponse response, Authentication authentication)
		throws IOException, ServletException {
		// TODO Auto-generated method stub
		LogUtil.error(getClass(), "登录成功");
		UserDTO user = new UserDTO();
		user.setUsername(authentication.getName());
		CookieUtil.addCookie(response, "name", user.getUsername(), 0);
//		String url=request.getHeader("Referer").substring(request.getHeader("Referer").indexOf('=')+2);
//		LogUtil.error(getClass(), url);
		response.sendRedirect("index");
		new JwtUtil().generateToken(response, user);
	}
	
}
