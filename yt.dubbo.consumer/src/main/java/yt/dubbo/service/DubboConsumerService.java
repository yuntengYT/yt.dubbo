package yt.dubbo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import yt.dubbo.model.UserDTO;

/**
 * @author yunteng
 */
@Component
@Slf4j
public class DubboConsumerService {

	@Reference
	private UserService userService;

	public Integer saveUser() {
		UserDTO userDTO = new UserDTO();
		log.info("save====666");
		userDTO.setAge(userService.sayHello(14).getAge());
		return userDTO.getAge();
	}
}
