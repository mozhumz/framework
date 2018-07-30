package top.lshaci.framework.permission.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;
import top.lshaci.framework.permission.service.ResourceService;
import top.lshaci.framework.permission.utils.ResourceUtils;

/**
 * The permission config
 * 
 * @author lshaci
 * @since 0.0.4
 */
@Slf4j
@Configuration
@PropertySource("classpath:permission.properties")
@MapperScan("com.xdbigdata.framework.permission.mapper")
@ComponentScan("com.xdbigdata.framework.permission.service")
public class PermissionConfig {
	
	/**
	 * Set ResourceUtils resourceService
	 * 
	 * @param resourceService the resourceService bean
	 */
	@Autowired
	public void setResourceService(ResourceService resourceService) {
		log.debug("Set ResourceUtils.resourceService...");
		ResourceUtils.resourceService = resourceService;
	}
}
