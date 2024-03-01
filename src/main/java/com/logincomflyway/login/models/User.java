package com.logincomflyway.login.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.logincomflyway.login.enums.EnumRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "tb_user")
public class User implements UserDetails, Serializable{// UserDetails, 
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	private String name;	
	
	private String email;//é o login, propriamente dito
	
	private String password;
	
	@Column(name = "user_role")
	private EnumRole user_role;

	
//	@ManyToMany(fetch = FetchType.EAGER) // metodo EAGER já traz os Roles respectivos do User
//	@JoinTable(
//			name = "tb_user_role", 
//			joinColumns = @JoinColumn(name = "id_user"), 
//			inverseJoinColumns = @JoinColumn(name = "id_role"))
//	private Set<Role> roles = new HashSet<>();

	
	public User() {
		
	}

	public User(Long id, String name, String email, String password, EnumRole user_role) {		
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.user_role = user_role;
	}
	
	public User(String name, String email, String password, EnumRole user_role) {	
		this.name = name;
		this.email = email;
		this.password = password;
		this.user_role = user_role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public EnumRole getUserRole() {
		return this.user_role;
	}

	public void setUserRole(EnumRole user_role) {
		this.user_role = user_role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id);
	}

		
	
	public boolean hasRole(String roleName) {

		return this.user_role.getRole().equals(roleName);
	}

	@Override 
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		if(this != null) {
			if(this.user_role == EnumRole.ADMIN) {
				return List.of(
						new SimpleGrantedAuthority("ROLE_ADMIN"),
						new SimpleGrantedAuthority("ROLE_USER")
						);
			}
			
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));			
		}
		
	return null;
	

	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		
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
