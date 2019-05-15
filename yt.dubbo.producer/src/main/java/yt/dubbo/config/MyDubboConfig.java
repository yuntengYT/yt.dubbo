package yt.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yunteng
 */

@Configuration
public class MyDubboConfig {


	@Bean
	@ConditionalOnMissingBean(ApplicationConfig.class)
	public ApplicationConfig applicationConfig() {
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName("boot-user-service-provider");
		return applicationConfig;
	}

	@Bean
	@ConditionalOnMissingBean(RegistryConfig.class)
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setProtocol("zookeeper");
		registryConfig.setAddress("zookeeper://10.112.99.147:2181");
		registryConfig.setTimeout(60000);
		return registryConfig;
	}

	@Bean
	@ConditionalOnMissingBean(ProtocolConfig.class)
	public ProtocolConfig protocolConfig() {
		ProtocolConfig protocolConfig = new ProtocolConfig();
		protocolConfig.setName("dubbo");
		protocolConfig.setPort(20880);
		protocolConfig.setThreads(200);
		return protocolConfig;
	}
}
