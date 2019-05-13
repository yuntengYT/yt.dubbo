package yt.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import yt.dubbo.model.UserDTO;
import yt.dubbo.service.UserService;

/**
 * @author yunteng
 */
@Service
public class UserServiceImpl implements UserService {
	@Override
	public UserDTO sayHello(Integer aa) {
		UserDTO userDTO = new UserDTO();
		userDTO.setAge(aa);
		userDTO.setName("aa");

		return userDTO;
	}
}
