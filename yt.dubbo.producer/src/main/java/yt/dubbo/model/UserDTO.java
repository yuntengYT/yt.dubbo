package yt.dubbo.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunteng
 */
@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = 4004132163444150913L;
	private String name;

	private Integer age;
}
