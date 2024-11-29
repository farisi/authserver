package com.sf.sso.app.services;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sf.sso.app.dtos.CustomUserDetails;

@Service
public class UserDetailConcret implements UserDetailsService{
	
	@Autowired
	UserService usrSrv;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		com.sf.sso.app.entities.User user = usrSrv.findByEmail(username);
		
		if(user == null) 
		{
			throw new UsernameNotFoundException(username);
		}
		
		CustomUserDetails usrDetail = new CustomUserDetails.Builder()
			.withUsername(user.getName())
			.withEmail(user.getEmail())
			.withFullname(user.getName())
			.withPassword(user.getPassword())
			.withAuthorities(Arrays.asList("ROLE_USER", "ROLE_ADMIN")
		            .stream()
		            .map(SimpleGrantedAuthority::new)
		            .collect(Collectors.toList()))
			.build();
		
		return usrDetail;
			
	}

}