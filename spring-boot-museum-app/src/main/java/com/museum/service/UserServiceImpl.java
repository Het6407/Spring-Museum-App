package com.museum.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.museum.model.Museum;
import com.museum.model.User;
import com.museum.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
   private  PasswordEncoder passwordEncoder;
	

	
	@Override
	public List<User> getAllUsers() { 
		return userRepository.findAll();
	}
	
	

	@Override
	public void saveUser(User user) {
		
		
		User userRepo = userRepository.findByEmail(user.getEmail());
		if(user.getId() == null) {
			if(userRepo != null && userRepo.getEmail() != null && !Strings.isEmpty(userRepo.getEmail()) && userRepo.getEmail().equals(user.getEmail())) {
				throw new InvalidParameterException("Email already exist");
			}
		}
		
		user.setRole("ADMIN");
		
		user.setStatus(true);
		
		user.setPassword(passwordEncoder.encode("user123"));
		
		
		
		userRepository.save(user);
     
		
		
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		
		User user = null;
		if(optional.isPresent()) {
			user = optional.get();		
			}
		else {
			throw new RuntimeException("User not found for id :: " + id);
		}
		return user;
	}

	@Override
	public void deleteUserById(Long id) {
		
		this.userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//System.out.println(userRepository.findByEmail(username));
		return userRepository.findByEmail(username);
		
	}

	@Override
	public void changeStatus(long id) {
		User user = userRepository.findById(id).orElse(null);
	
		if(user.getStatus()){
			user.setStatus(false);
			userRepository.save(user);	
		}else {
			user.setStatus(true);
			userRepository.save(user);	
		}
	}



	@Override
	public User findByUsername(String userName) {
		
		return userRepository.findByUsername(userName);
	}



	@Override
	public User findById(Long id) {
		
		return userRepository.findById(id).get();
	}

	 
	

	
}
