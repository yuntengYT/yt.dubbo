package yt.dubbo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import yt.dubbo.model.UserDTO;

/**
 * @author yunteng
 */
@Component
public class DubboConsumerService {

	@Reference
	private UserService userService;

	public Integer saveUser() {
		UserDTO userDTO = new UserDTO();
		userDTO.setAge(userService.sayHello(14).getAge());
		return userDTO.getAge();
	}
}
