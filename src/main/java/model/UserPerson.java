package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
	@NamedQuery(name = "UserPerson.findAll", query = "select u from UserPerson u"),
	@NamedQuery(name = "UserPerson.findByName", query = "select u from UserPerson u where u.name = :name")
})
public class UserPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String surname;
	private String email;
	private String login;
	private String password;
	private Integer age;
	private String sex;
	
	@OneToMany(mappedBy = "userPerson", fetch = FetchType.EAGER)
	private List<TelephoneUser> telephoneUsers;
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<TelephoneUser> getTelephoneUsers() {
		return telephoneUsers;
	}
	
	public void setTelephoneUsers(List<TelephoneUser> telephoneUsers) {
		this.telephoneUsers = telephoneUsers;
	}

	@Override
	public String toString() {
		return "UserPerson [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email
				+ ", login=" + login + ", password=" + password + ", age=" + age + ", sex=" + sex + ", telephoneUsers=" + telephoneUsers
				+ "]";
	}

	
	
	

}
