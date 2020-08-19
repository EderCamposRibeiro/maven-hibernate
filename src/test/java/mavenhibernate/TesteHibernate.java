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
		person.setEmail("edercribeiro@gmail.com");

		daoGeneric.salvar(person);

	}

	@Test
	public void testeBuscar() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = new UserPerson();

		person.setId(12L);

		person = daoGeneric.pesquisar(person);

		System.out.println(person);

	}

	@Test
	public void testeBuscar2() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = daoGeneric.pesquisar2(11L, UserPerson.class);

		System.out.println(person);

	}

	@Test
	public void testeUpdate() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = daoGeneric.pesquisar2(11L, UserPerson.class);

		person.setAge(99);
		person.setName("Nome Atualizado Hibernate");

		daoGeneric.updateMerge(person);

		System.out.println(person);

	}

	@Test
	public void testeDelete() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		UserPerson person = daoGeneric.pesquisar2(19L, UserPerson.class);

		daoGeneric.deletarPorId(person);

	}

	@Test
	public void testeConsultar() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		List<UserPerson> list = daoGeneric.listar(UserPerson.class);

		for (UserPerson usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}

	}

	@Test
	public void testQueryList() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();

		List<UserPerson> list = daoGeneric.getEntityManager().createQuery(" from UserPerson where name = 'Eder'")
				.getResultList();

		for (UserPerson usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
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

		for (UserPerson usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
	}
	
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		
		List<UserPerson> list = daoGeneric.getEntityManager().
				createQuery(" from UserPerson where name = :name").
				setParameter("name", "Eder").getResultList();
		
		for (UserPerson usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
		
	}
	
	@Test
	public void testeQuerySomaIdade() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		
		Long addAge = (Long) daoGeneric.getEntityManager().
				createQuery("select sum(u.age) from UserPerson u ").
				getSingleResult();
		
		System.out.println("Soma das Idades = " + addAge);
	}	
	
	@Test
	public void testeQueryMediaIdade() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		
		Double averageAge = (Double) daoGeneric.getEntityManager().
				createQuery("select avg(u.age) from UserPerson u ").
				getSingleResult();
		
		System.out.println("Média das Idades = " + averageAge);
	}	
	
	@Test
	public void testeNamedQuery1() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		List<UserPerson> list = daoGeneric.getEntityManager().
				createNamedQuery("UserPerson.findAll").getResultList();
		
		for (UserPerson userPerson : list) {
			System.out.println(userPerson);
			System.out.println("-------------------------");
		}
		
	}
	
	@Test
	public void testeNamedQuery2() {
		DaoGeneric<UserPerson> daoGeneric = new DaoGeneric<UserPerson>();
		List<UserPerson> list = daoGeneric.getEntityManager().
				createNamedQuery("UserPerson.findByName").
				setParameter("nome", "Eder").
				getResultList();
		
		for (UserPerson usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
		
	}	
	
	@Test
	public void testSetPhone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UserPerson person = (UserPerson) daoGeneric.pesquisar2(22L, UserPerson.class);
		
		TelephoneUser telephoneUser = new TelephoneUser();
		telephoneUser.setType("Work");
		telephoneUser.setNumber("6122224455");
		telephoneUser.setUserPerson(person);
		
		daoGeneric.salvar(telephoneUser);
		
	}
	
	@Test
	public void testFindPhones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UserPerson person = (UserPerson) daoGeneric.pesquisar2(21L, UserPerson.class);
		
		for (TelephoneUser phone : person.getTelephoneUsers()) {
			System.out.println(phone.getUserPerson().getName());			
			System.out.println(phone.getType());
			System.out.println(phone.getNumber());
			System.out.println("-------------------------");
		}
		
	}	

}

















