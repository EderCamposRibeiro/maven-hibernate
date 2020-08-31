package managedBean;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import dao.DaoPhone;
import dao.DaoUser;
import model.TelephoneUser;
import model.UserPerson;

@ManagedBean(name = "telephoneManagedBean")
@ViewScoped
public class TelephoneManagedBean {
	
	private UserPerson user = new UserPerson();
	private TelephoneUser phoneuser = new TelephoneUser();
	private DaoUser<UserPerson> daoUser = new DaoUser<UserPerson>();
	private DaoPhone<TelephoneUser> daoPhone = new DaoPhone<TelephoneUser>();
	
	@PostConstruct
	public void init ( ) {
		
		String usercode = FacesContext.getCurrentInstance().getExternalContext()
			.getRequestParameterMap().get("usercode");
		user = daoUser.find2(Long.parseLong(usercode), UserPerson.class);
	}
	
	public String save() {
		phoneuser.setUserPerson(user);
		daoPhone.save(phoneuser);
		phoneuser = new TelephoneUser();
		user = daoUser.find2(user.getId(), UserPerson.class);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Save Successfully!"));
		return "";
	}
	
	public String deletePhone() throws Exception {
		daoPhone.deleteById(phoneuser);
		user = daoUser.find2(user.getId(), UserPerson.class);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Removed Successfully!"));
		phoneuser = new TelephoneUser();
		return "";
	}
	
	public void setUser(UserPerson user) {
		this.user = user;
	}
	
	public UserPerson getUser() {
		return user;
	}
	
	public void setDaoPhone(DaoPhone<TelephoneUser> daoPhone) {
		this.daoPhone = daoPhone;
	}
	
	public DaoPhone<TelephoneUser> getDaoPhone() {
		return daoPhone;
	}
	
	public void setPhoneuser(TelephoneUser phoneuser) {
		this.phoneuser = phoneuser;
	}
	
	public TelephoneUser getPhoneuser() {
		return phoneuser;
	}
	
	
}
