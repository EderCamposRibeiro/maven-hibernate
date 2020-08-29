package managedBean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoGeneric;
import model.UserPerson;

@ManagedBean(name = "userPersonManagedBean")
@ViewScoped
public class UserPersonManagedBean {

	private UserPerson userPerson = new UserPerson();
	private DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
	private List<UserPerson> list = new ArrayList<UserPerson>();
	
	/*After the Bean were constructed in the memmory this method will be executed*/
	@PostConstruct
	public void init() {
		list = daoGeneric.list(UserPerson.class);
	}

	public UserPerson getUserPerson() {
		return userPerson;
	}

	public void setUserPerson(UserPerson userPerson) {
		this.userPerson = userPerson;
	}

	public String save() {
		daoGeneric.save(userPerson);
		list.add(userPerson);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Save Successfully!"));
		return ""; /*If returns Null stays on the same screen*/
	}
	
	public String newPerson() {
		userPerson = new UserPerson();/*Erase the screen*/
		return "";
	}
	
	public List<UserPerson> getList() {
		return list;
	}
	
	public String delete() {
		try {
			daoGeneric.deleteById(userPerson);
			list.remove(userPerson);
			userPerson = new UserPerson();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Removed Successfully!"));
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Information: ", "Cannot delete - Exist phone number for the user!"));
			}
			e.printStackTrace();
		}
		

		return "";
	}
}
