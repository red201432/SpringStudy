package util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

@Component
public class MybaitsUtil {
	private static  SqlSessionFactory factory;
	
	static{
		try{
			//获取资源配置文件
			InputStream inputStream=Resources.getResourceAsStream("mybaits-config.xml");
			//获取SqlSessionFactory对象
			factory=new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	/*
	 * 获取Session对象
	 */
	public static SqlSession getSqlSession() {
		return factory.openSession();
	}
	
	public static SqlSessionFactory factory(Class<?> cla){
		factory.getConfiguration().addMapper(cla);
		return factory;
	}
	/*
	 * Close SqlSession
	 */
	public static  void  CloseSession(SqlSession session) {
		if(null != session ){
			session.close();
		}
		session=null;
	}
	
	/*
	 * 获取sqlSession
	 * @param isAutoCommit
	 * true 表示sqlSession在执行完sql之后会自动提交事务
	 * false 表示sqlsession在执行完sql之后不会自动提交事务，需要手动调用 sqlsession.commit()提交事务
	 */
	public static SqlSession getSqlSession(boolean isAutoCommit){
		return factory.openSession(isAutoCommit);
	}

}
