package yt.dubbo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yt.dubbo.service.DubboConsumerService;

/**
 * @author yunteng
 */
@RestController
@Slf4j
public class UserController {

	@Autowired
	DubboConsumerService sayHello;
	@RequestMapping("/save")
	public Object saveUser() {

		log.info("save====");
		return sayHello.saveUser();
	}
}
