package yt.dubbo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yt.dubbo.service.DubboConsumerService;

/**
 * @author yunteng
 */
@RestController
public class UserController {

	@Autowired
	DubboConsumerService sayHello;
	@RequestMapping("/save")
	public Object saveUser() {

		return sayHello.saveUser();
	}
}
