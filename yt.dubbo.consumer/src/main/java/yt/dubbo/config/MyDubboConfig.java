package yt.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
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
		applicationConfig.setName("boot-user-service-consumer");
		return applicationConfig;
	}

	@Bean
	@ConditionalOnMissingBean(RegistryConfig.class)
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setProtocol("zookeeper");
		registryConfig.setAddress("zookeeper://10.112.99.147:2181");
		return registryConfig;
	}
	@Bean
	@ConditionalOnMissingBean(ConsumerConfig.class)
	public ConsumerConfig consumerConfig() {
		ConsumerConfig consumerConfig = new ConsumerConfig();
		consumerConfig.setCheck(false);
		consumerConfig.setTimeout(300000);
		consumerConfig.setRetries(9);
		System.out.println(111111111);
		return consumerConfig;
	}
}
