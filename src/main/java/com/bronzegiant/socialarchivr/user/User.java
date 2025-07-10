package com.bronzegiant.socialarchivr.user;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstname;
	private String lastname;
	private String email;
	
	@Column(name = "last_archive_date")
	private LocalDateTime lastArchivedDate = LocalDateTime.now();

	@Column(name = "create_date")
	private LocalDateTime createDate = LocalDateTime.now();
	
	User(){
	}
	
	User(String fn, String ln, String email){
		this.firstname = fn;
		this.lastname = ln;
		this.email = email;
	}
	
	public long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public LocalDateTime getLastArchivedDate() {
		return lastArchivedDate;
	}

	public void setLastArchivedDate(LocalDateTime lastArchivedDate) {
		this.lastArchivedDate = lastArchivedDate;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(this.id, user.id) && Objects.equals(this.firstname, user.firstname)
				&& Objects.equals(this.lastname, user.lastname) && Objects.equals(this.email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.email);
	}

	@Override
	public String toString() {
		return "Employee{" + "id=" + this.id + ", name='" + this.firstname + '\'' + this.lastname + '\'' + ", email='"
				+ this.email + '\'' + '}';
	}
}
