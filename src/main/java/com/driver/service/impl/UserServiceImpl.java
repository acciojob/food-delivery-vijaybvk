package com.driver.service.impl;


import com.driver.io.entity.UserEntity;
import com.driver.io.repository.UserRepository;
import com.driver.service.UserService;
import com.driver.shared.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(user.getUserId());
        if(userEntity!=null){
            throw new Exception("User Already Exists ");
        }
        UserEntity userEntity2=UserEntity.builder().userId(user.getUserId()).email(user.getEmail()).firstName(user.getFirstName())
                .lastName(user.getLastName()).build();
        userRepository.save(userEntity2);
        return user;
    }

    @Override
    public UserDto getUser(String email) throws Exception {
        UserEntity userEntity=userRepository.findByEmail(email);
        if(userEntity==null){
            throw new Exception("User doesn't exist");
        }
        UserDto userDto=UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId()).firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).email(userEntity.getEmail()).build();
        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new Exception("User doesn't exist");
        }
        UserDto userDto=UserDto.builder().id(userEntity.getId()).userId(userEntity.getUserId()).firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).email(userEntity.getEmail()).build();
        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) throws Exception {
        UserEntity userEntity=userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new Exception("User doesn't exist");
        }
        UserDto userDto=user;
        userDto.setId(userEntity.getId());
        userRepository.updateUserEntity(userEntity.getId(), user.getUserId(), user.getFirstName(), user.getLastName(), user.getEmail());
        return userDto;
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null){
            throw new Exception("User doesn't exist");
        }
        userRepository.deleteById(userEntity.getId());
    }


    @Override
    public List<UserDto> getUsers() {
        List<UserEntity> userEntities= (List<UserEntity>) userRepository.findAll();
        List<UserDto> userDtos = null;
        for(UserEntity userEntity:userEntities){
            userDtos.add(UserDto.builder().userId(userEntity.getUserId()).firstName(userEntity.getFirstName()).lastName(userEntity.getLastName()).email(userEntity.getEmail()).id(userEntity.getId()).build());
        }
        return userDtos;
    }
}