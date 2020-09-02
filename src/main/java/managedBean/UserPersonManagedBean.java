package managedBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import com.google.gson.Gson;

import dao.DaoUser;
import model.UserPerson;

@ManagedBean(name = "userPersonManagedBean")
@ViewScoped
public class UserPersonManagedBean {

	private UserPerson userPerson = new UserPerson();
	private List<UserPerson> list = new ArrayList<UserPerson>();
	private DaoUser<UserPerson> daoGeneric = new DaoUser<UserPerson>();

	/*
	 * After the Bean were constructed in the memmory this method will be executed
	 */
	@PostConstruct
	public void init() {
		list = daoGeneric.list(UserPerson.class);
	}

	public void findCep(AjaxBehaviorEvent event) {
		try {
			
			URL url = new URL("https://viacep.com.br/ws/"+ userPerson.getCep() +"/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			
			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			
			UserPerson userCepPerson = new Gson().
					fromJson(jsonCep.toString(), UserPerson.class);
			
			userPerson.setCep(userCepPerson.getCep());
			userPerson.setLogradouro(userCepPerson.getLogradouro());
			userPerson.setComplemento(userCepPerson.getComplemento());
			userPerson.setBairro(userCepPerson.getBairro());
			userPerson.setLocalidade(userCepPerson.getLocalidade());
			userPerson.setUf(userCepPerson.getUf());
			userPerson.setIbge(userCepPerson.getIbge());
			userPerson.setGia(userCepPerson.getGia());
			userPerson.setDdd(userCepPerson.getDdd());
			userPerson.setSiafi(userCepPerson.getSiafi());
			
			System.out.println(userPerson);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		return ""; /* If returns Null stays on the same screen */
	}

	public String newPerson() {
		userPerson = new UserPerson();/* Erase the screen */
		return "";
	}

	public List<UserPerson> getList() {
		return list;
	}

	public String delete() {
		try {
			daoGeneric.deleteUser(userPerson);
			list.remove(userPerson);
			userPerson = new UserPerson();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Removed Successfully!"));
		} catch (Exception e) {
			if (e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Information: ", "Cannot delete - Exist phone number for the user!"));
			} else {
				e.printStackTrace();
			}

		}

		return "";
	}
}
