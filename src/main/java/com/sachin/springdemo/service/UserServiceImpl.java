package com.sachin.springdemo.service;

import com.sachin.springdemo.dao.RoleRepository;
import com.sachin.springdemo.dao.UserRepository;
import com.sachin.springdemo.entity.Role;
import com.sachin.springdemo.entity.User;
import com.sachin.springdemo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	// need to inject user dao
	/*
	 * @Autowired private UserDao userDao;
	 */

	@Autowired
	private UserRepository userDao;
	
	/*
	 * @Autowired private RoleDao roleDao;
	 */
	
	@Autowired
	private RoleRepository roleDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User findByUserName(String userName) {
		// check the database if the user already exists
		List<User> userList = userDao.findByUserName(userName);
		return userList.isEmpty() ? null : userList.get(0);
	}

	@Override
	@Transactional
	public void save(CrmUser crmUser) {
		User user = new User();
		 // assign user details to the user object
		user.setUserName(crmUser.getUserName());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstName(crmUser.getFirstName());
		user.setLastName(crmUser.getLastName());
		user.setEmail(crmUser.getEmail());

		String formRole = crmUser.getFormRole();
		
		Collection<Role> roles = new ArrayList<>();
		roles.add(roleDao.findRoleByName("ROLE_EMPLOYEE").get(0));
		
		if (!formRole.equals("ROLE_EMPLOYEE")) {
			roles.add(roleDao.findRoleByName(formRole).get(0));
	    }
		
		// give user default role of "employee"
		user.setRoles(roles);
		
		// save user in the database
		userDao.save(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<User> userList = userDao.findByUserName(userName); 
		User user = userList.get(0);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
