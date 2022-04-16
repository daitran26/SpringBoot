package com.spring.baitap10.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user",uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"}),
		@UniqueConstraint(columnNames = {"email"})
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotBlank
	@Size(min= 3, max = 50)
	private String name;
	@NotBlank
	@Size(min= 3, max = 50)
	private String username;
	@JsonIgnore
	@NotBlank
	private String password;
	@Email
	@NotBlank
	private String email;
	@Lob
	private String avatar;
	private String phone;
	private String address;
	private String verificationCode;
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
	Set<Role> roles = new HashSet<>();
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // fix bi-direction toString() recursion problem
    private Cart cart;
	public User(@NotBlank @Size(min = 3, max = 50) String name, @NotBlank @Size(min = 3, max = 50) String username,
			@NotBlank String password, @Email @NotBlank String email, String avatar) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
	}
	
}
