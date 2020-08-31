package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import mavenhibernate.HibernateUtil;

public class DaoGeneric<E> {

	private EntityManager entityManager = HibernateUtil.getEntityManager();

	public void save(E entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(entity);
		transaction.commit();
	}

	public E updateMerge(E entity) { /* Save or update */
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		E entitySaved = entityManager.merge(entity);
		transaction.commit();

		return entitySaved;
	}

	public E find(E entity) {
		Object id = HibernateUtil.getPrimaryKey(entity);
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + ((Class<E>) entity).getSimpleName() +
				" where id = " + id).getSingleResult();

		return e;
	}

	public E find2(Long id, Class<E> entity) {
		entityManager.clear();
		E e = (E) entityManager.createQuery("from " + entity.getSimpleName() +
				" where id = " + id).getSingleResult();

		return e;
	}

	public void deleteById(E entity) throws Exception{

		Object id = HibernateUtil.getPrimaryKey(entity);

		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		entityManager
				.createNativeQuery(
						"delete from " + entity.getClass().getSimpleName().toLowerCase() + " where id = " + id)
				.executeUpdate(); // Do the deletion

		transaction.commit(); // Save the change/transaction on Database!!!
	}

	public List<E> list(Class<E> entity) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();

		List<E> list = entityManager.createQuery("from " + entity.getName()).getResultList();

		transaction.commit();

		return list;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
