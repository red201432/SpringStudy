package util;

import org.slf4j.LoggerFactory;

public class LogUtil {
	public static void error(Class T,String str){
		LoggerFactory.getLogger(T).error(str);
	}
	public static void info(Class T,String str){
		LoggerFactory.getLogger(T).info(str);
	}
	
	public static void warn(Class T,String str){
		LoggerFactory.getLogger(T).warn(str);
	}
	
	public static void debug(Class T,String str){
		LoggerFactory.getLogger(T).debug(str);
	}
}
