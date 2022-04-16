package com.spring.baitap10.security.userprincal;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.baitap10.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPrinciple implements UserDetails{

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private String username;
	@JsonIgnore
	private String password;
	private String email;
	private String avatar;
	private String phone;
	private String adress;
	private Collection<? extends GrantedAuthority> roles;
	public static UserPrinciple build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		return new UserPrinciple(
				user.getId(),user.getName(),user.getUsername(),user.getPassword(),user.getEmail(),user.getAvatar(),user.getPhone(),user.getAddress(),authorities
				);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	public String getAvatar() {
		// TODO Auto-generated method stub
		return avatar;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
