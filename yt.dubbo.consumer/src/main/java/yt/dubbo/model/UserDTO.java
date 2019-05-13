package yt.dubbo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

	String name;

	Integer age;
}
