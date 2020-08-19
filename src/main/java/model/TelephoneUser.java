package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TelephoneUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String type;
	
	@Column(nullable = false)
	private String number;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private UserPerson userPerson;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public UserPerson getUserPerson() {
		return userPerson;
	}
	
	public void setUserPerson(UserPerson userPerson) {
		this.userPerson = userPerson;
	}

	@Override
	public String toString() {
		return "TelephoneUser [id=" + id + ", type=" + type + ", number=" + number + ", usuarioPessoa=" + userPerson
				+ "]";
	}
	
	
	
	
}
