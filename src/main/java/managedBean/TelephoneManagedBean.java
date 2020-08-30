package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoUser;
import model.UserPerson;

@ManagedBean(name = "telephoneManagedBean")
@ViewScoped
public class TelephoneManagedBean {
	
	private UserPerson user = new UserPerson();
	private DaoUser<UserPerson> daoUser = new DaoUser<UserPerson>();
	
	@PostConstruct
	public void init ( ) {
		
		String usercode = FacesContext.getCurrentInstance().getExternalContext()
			.getRequestParameterMap().get("usercode");
		user = daoUser.find2(Long.parseLong(usercode), UserPerson.class);

		
	}
	
	public void setUser(UserPerson user) {
		this.user = user;
	}
	
	public UserPerson getUser() {
		return user;
	}
	
}
