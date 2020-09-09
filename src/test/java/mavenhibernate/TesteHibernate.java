package mavenhibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelephoneUser;
import model.UserPerson;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = new UserPerson();

		person.setAge(36);
		person.setLogin("teste 3");
		person.setName("Eder");
		person.setPassword("123");
		person.setSurname("Mazzoccante");

		daoGeneric.save(person);

	}

	@Test
	public void testFind() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = new UserPerson();

		person.setId(12L);

		person = daoGeneric.find(person);

		System.out.println(person);

	}

	@Test
	public void testFind2() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = daoGeneric.find2(11L, UserPerson.class);

		System.out.println(person);

	}

	@Test
	public void testUpdate() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = daoGeneric.find2(11L, UserPerson.class);

		person.setAge(99);
		person.setName("Updated Name Hibernate");

		daoGeneric.updateMerge(person);

		System.out.println(person);

	}

	@Test
	public void testDelete() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = daoGeneric.find2(19L, UserPerson.class);

		try {
			daoGeneric.deleteById(person);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testConsult() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		List<UserPerson> list = daoGeneric.list(UserPerson.class);

		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}

	}

	@Test
	public void testQueryList() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		List<UserPerson> list = daoGeneric.getEntityManager().createQuery(" from UserPerson where name = 'Eder'")
				.getResultList();

		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}

	}

	@Test
	public void testQueryListMaxResult() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		List<UserPerson> list = daoGeneric.
				getEntityManager().
				createQuery(" from UserPerson order by id").
				setMaxResults(3).
				getResultList();

		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}
	}
	
	@Test
	public void testQueryListParameter() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		
		List<UserPerson> list = daoGeneric.getEntityManager().
				createQuery(" from UserPerson where name = :name").
				setParameter("name", "Eder").getResultList();
		
		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}
		
	}
	
	@Test
	public void testQueryAddAges() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		
		Long addAge = (Long) daoGeneric.getEntityManager().
				createQuery("select sum(u.age) from UserPerson u ").
				getSingleResult();
		
		System.out.println("Sum of Ages = " + addAge);
	}	
	
	@Test
	public void testQueryAverageAge() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		
		Double averageAge = (Double) daoGeneric.getEntityManager().
				createQuery("select avg(u.age) from UserPerson u ").
				getSingleResult();
		
		System.out.println("Average Ages = " + averageAge);
	}	
	
	@Test
	public void testNamedQuery1() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		List<UserPerson> list = daoGeneric.getEntityManager().
				createNamedQuery("UserPerson.findAll").getResultList();
		
		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}
		
	}
	
	@Test
	public void testNamedQuery2() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		List<UserPerson> list = daoGeneric.getEntityManager().
				createNamedQuery("UserPerson.findByName").
				setParameter("nome", "Eder").
				getResultList();
		
		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}
		
	}	
	
	@Test
	public void testSetPhone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UserPerson person = (UserPerson) daoGeneric.find2(22L, UserPerson.class);
		
		TelephoneUser telephoneUser = new TelephoneUser();
		telephoneUser.setType("Work");
		telephoneUser.setNumber("6122224455");
		telephoneUser.setUserPerson(person);
		
		daoGeneric.save(telephoneUser);
		
	}
	
	@Test
	public void testFindPhones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UserPerson person = (UserPerson) daoGeneric.find2(21L, UserPerson.class);
		
		for (TelephoneUser phone : person.getTelephoneUsers()) {
			System.out.println(phone.getUserPerson().getName());			
			System.out.println(phone.getType());
			System.out.println(phone.getNumber());
			System.out.println("-------------------------");
		}
		
	}	

}

















