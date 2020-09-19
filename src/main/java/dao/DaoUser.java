package dao;

import java.util.List;

import javax.persistence.Query;

import model.UserPerson;

public class DaoUser<E> extends DaoGeneric<UserPerson>{
	
	public void deleteUser(UserPerson person) throws Exception {
		getEntityManager().getTransaction().begin();
		getEntityManager().remove(person);
		getEntityManager().getTransaction().commit();
		super.deleteById(person);
	}

	public List<UserPerson> find(String searchField) {
		Query query = super.getEntityManager().createQuery(" from UserPerson u where name like '%"+ searchField +"%'");
		return query.getResultList();
	}

}
