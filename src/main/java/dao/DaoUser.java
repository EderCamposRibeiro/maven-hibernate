package dao;

import model.UserPerson;

public class DaoUser<E> extends DaoGeneric<UserPerson>{
	
	public void deleteUser(UserPerson person) throws Exception {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(person);
		getEntityManager().getTransaction().commit();
		super.deleteById(person);
	}

}
