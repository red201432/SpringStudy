package service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import model.UserRole;
import util.MybaitsUtil;

@Service
public class RoleService {
	public static String getUserRoleList(String name){
		SqlSession session=MybaitsUtil.getSqlSession();
		String statement="main.java.mappings.Mapper.getUserRoleList";
//		Collection<UserRole> list=session.selectList(statement,name);
		UserRole rolename=session.selectOne(statement,name);
		if(rolename != null) return rolename.getUserType();
		return "";
	}
}
