package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import dao.DaoGeneric;
import model.UserPerson;

@ManagedBean(name = "userPersonManagedBean")
@ViewScoped
public class UserPersonManagedBean {

	private UserPerson userPerson = new UserPerson();
	private DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
	private List<UserPerson> list = new ArrayList<UserPerson>();

	public UserPerson getUserPerson() {
		return userPerson;
	}

	public void setUserPerson(UserPerson userPerson) {
		this.userPerson = userPerson;
	}

	public String save() {
		daoGeneric.save(userPerson);
		return ""; /*If returns Null stays on the same screen*/
	}
	
	public String newPerson() {
		userPerson = new UserPerson();/*Erase the screen*/
		return "";
	}
	
	public List<UserPerson> getList() {
		list = daoGeneric.list(UserPerson.class);
		return list;
	}
	
	public String delete() {
		daoGeneric.deleteById(userPerson);
		userPerson = new UserPerson();
		return "";
	}
}
