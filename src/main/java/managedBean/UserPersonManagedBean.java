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

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.google.gson.Gson;

import dao.DaoEmail;
import dao.DaoUser;
import model.EmailUser;
import model.UserPerson;

@ManagedBean(name = "userPersonManagedBean")
@ViewScoped
public class UserPersonManagedBean {

	private UserPerson userPerson = new UserPerson();
	private List<UserPerson> list = new ArrayList<UserPerson>();
	private DaoUser<UserPerson> daoGeneric = new DaoUser<UserPerson>();
	private BarChartModel barChartModel = new BarChartModel();
	private EmailUser emailUser = new EmailUser();
	private DaoEmail<EmailUser> daoEmail = new DaoEmail<EmailUser>();
	private String searchField;

	/*
	 * After the Bean were constructed in the memmory this method will be executed
	 */
	@PostConstruct
	public void init() {
		list = daoGeneric.list(UserPerson.class);

		buildGraphic();
	}

	private void buildGraphic() {
		barChartModel = new BarChartModel();

		/* Group of Employees */
		ChartSeries userSalary = new ChartSeries();

		userSalary.setLabel("Users");

		/* Add salary to the group */
		for (UserPerson userPerson : list) {
			/* Add salary */
			userSalary.set(userPerson.getName(), userPerson.getSalary());
		}
		/* add the group in the BarModel */
		barChartModel.addSeries(userSalary);
		barChartModel.setTitle("Employees' Salary");
	}

	public BarChartModel getBarChartModel() {
		return barChartModel;
	}

	public void findCep(AjaxBehaviorEvent event) {
		try {

			URL url = new URL("https://viacep.com.br/ws/" + userPerson.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}

			UserPerson userCepPerson = new Gson().fromJson(jsonCep.toString(), UserPerson.class);

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
		init(); //To recharges the BarChart
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
			init(); //To recharges the BarChart
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

	public EmailUser getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(EmailUser emailUser) {
		this.emailUser = emailUser;
	}

	public void addEmail() {
		emailUser.setUserPerson(userPerson);
		emailUser = daoEmail.updateMerge(emailUser);
		userPerson.getEmailUsers().add(emailUser);
		emailUser = new EmailUser();
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Save Successfully!"));
	}

	public void deleteEmail() throws Exception {
		String emailcode = FacesContext.getCurrentInstance().
				getExternalContext().getRequestParameterMap().get("emailcode");
		EmailUser remove = new EmailUser();
		remove.setId(Long.parseLong(emailcode));
		daoEmail.deleteById(remove);
		userPerson.getEmailUsers().remove(remove);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Information: ", "Removed Successfully!"));
		
	}
	
	public void search() {
		list = daoGeneric.find(searchField);
		buildGraphic();
	}
	
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	
	public String getSearchField() {
		return searchField;
	}
}
