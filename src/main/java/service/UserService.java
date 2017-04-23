package service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import model.TUser;
import util.JwtUtil;
import util.LogUtil;
import util.MybaitsUtil;

@Service
public class UserService implements UserDetailsService {
//	private SqlSession session=MybaitsUtil.getSqlSession();
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		TUser user=null;
		SqlSession session=MybaitsUtil.getSqlSession();
		try{			
			String statement="main.java.mappings.Mapper.getUserByName";
			user=session.selectOne(statement,username);
		}catch(Exception ex){
			LogUtil.error(getClass(), ex.getMessage());
		}finally {
			MybaitsUtil.CloseSession(session);
		}
		if(user==null) throw new UsernameNotFoundException("用户名不存在");
		
		Collection<GrantedAuthority> grantedAuth =new ArrayList<GrantedAuthority>(); 
		grantedAuth.add(new SimpleGrantedAuthority("USER"));
		UserDetails userDetails=new org.springframework.security.core.userdetails
				.User(user.getUsername(), user.getUsername(), true, true, true, true, grantedAuth);
		return userDetails;
	}
}
