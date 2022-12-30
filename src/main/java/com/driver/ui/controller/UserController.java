package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.UserDetailsRequestModel;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.model.response.UserResponse;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) throws Exception{
		UserDto userDto=userService.getUserByUserId(id);
		UserResponse userResponse=null;
		if(userDto!=null){
			userResponse=UserResponse.builder().email(userDto.getEmail()).firstName(userDto.getFirstName())
					.lastName(userDto.getLastName()).build();
		}
		return userResponse;
	}

	@PostMapping()
	public UserResponse createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto=UserDto.builder().email(userDetails.getEmail()).firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName()).build();
		UserDto userDto1=userService.createUser(userDto);
		UserResponse userResponse=null;
		if(userDto1!=null){
			userResponse=UserResponse.builder().email(userDto1.getEmail()).firstName(userDto1.getFirstName())
					.lastName(userDto1.getLastName()).build();
		}
		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) throws Exception{
		UserDto userDto=UserDto.builder().email(userDetails.getEmail()).firstName(userDetails.getFirstName())
				.lastName(userDetails.getLastName()).build();
		UserDto userDto1=userService.updateUser(id, userDto);
		UserResponse userResponse=null;
		if(userDto1!=null){
			userResponse=UserResponse.builder().email(userDto1.getEmail()).firstName(userDto1.getFirstName())
					.lastName(userDto1.getLastName()).build();
		}
		return userResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel=OperationStatusModel.builder().operationName(String.valueOf(RequestOperationName.DELETE)).build();
		userService.deleteUser(id);
		return null;
	}

	@GetMapping()
	public List<UserResponse> getUsers(){
		List<UserDto> userDtos=userService.getUsers();
		List<UserResponse> userResponses=new ArrayList<>();
		for(UserDto userDto:userDtos){
			UserResponse userResponse=UserResponse.builder().email(userDto.getEmail()).firstName(userDto.getFirstName())
					.lastName(userDto.getLastName()).build();
			userResponses.add(userResponse);
		}
		return userResponses;
	}

}