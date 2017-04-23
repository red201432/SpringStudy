import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import util.LogUtil;
@ComponentScan(basePackages={"controller","service","configuration","security"})//添加需要扫描的包，默认扫描同一包下的文件
@SpringBootApplication
public class Application implements EmbeddedServletContainerCustomizer {
	 public static void main(String[] args) {
	    	LogUtil.error(Application.class, "Start Server!!!");
	        SpringApplication.run(Application.class, args);
	    }
	    
	 public void customize(ConfigurableEmbeddedServletContainer container){
	    	container.setPort(9000);
	    }
	 
}
