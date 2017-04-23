package util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	public static void addCookie(HttpServletResponse response,String name, String value, int maxAge){
		String val = RSAUtil.encryptDES(value);
		Cookie cookie = new Cookie(name,val);
		cookie.setPath("/");
		if (maxAge > 0) cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}
	public static Cookie getCookieByName(HttpServletRequest request, String cookieName) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if(cookieMap.containsKey(cookieName)){
			Cookie cookie = (Cookie)cookieMap.get(cookieName);
			return cookie;
		}
		return null;
	}
	
	public static String getCookieValueByName(HttpServletRequest request,String cookieName) {
		return RSAUtil.decryptDES(getCookieByName(request, cookieName).getValue());
	}
	
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request){
		Map<String, Cookie> cookieMap = new HashMap<String,Cookie>();
		Cookie[] cookies = request.getCookies();
		if( null != cookies){
			for(Cookie cookie : cookies){
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
