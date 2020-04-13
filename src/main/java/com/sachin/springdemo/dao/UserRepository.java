package com.sachin.springdemo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sachin.springdemo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findByUserName(String userName);
}
