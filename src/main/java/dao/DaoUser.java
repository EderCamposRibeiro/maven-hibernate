package dao;

import model.UserPerson;

public class DaoUser<E> extends DaoGeneric<UserPerson>{
	
	public void deleteUser(UserPerson person) throws Exception {
		getEntityManager().getTransaction().begin();
		String sqlDeletePhone = "delete from telephoneuser where userperson_id = "
				+ person.getId(); 
		
		getEntityManager().createNativeQuery(sqlDeletePhone).executeUpdate();
		getEntityManager().getTransaction().commit();
		
		super.deleteById(person);
	}

}
