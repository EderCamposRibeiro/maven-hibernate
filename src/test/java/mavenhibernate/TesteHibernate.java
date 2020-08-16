package mavenhibernate;

import java.util.List;

import org.junit.Test;

import dao.DaoGeneric;
import model.TelephoneUser;
import model.UsuarioPessoa;

public class TesteHibernate {

	@Test
	public void testeHibernateUtil() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setIdade(36);
		pessoa.setLogin("teste 3");
		pessoa.setNome("Eder");
		pessoa.setSenha("123");
		pessoa.setSobrenome("Mazzoccante");
		pessoa.setEmail("edercribeiro@gmail.com");

		daoGeneric.salvar(pessoa);

	}

	@Test
	public void testeBuscar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = new UsuarioPessoa();

		pessoa.setId(12L);

		pessoa = daoGeneric.pesquisar(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void testeBuscar2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar2(11L, UsuarioPessoa.class);

		System.out.println(pessoa);

	}

	@Test
	public void testeUpdate() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar2(11L, UsuarioPessoa.class);

		pessoa.setIdade(99);
		pessoa.setNome("Nome Atualizado Hibernate");

		daoGeneric.updateMerge(pessoa);

		System.out.println(pessoa);

	}

	@Test
	public void testeDelete() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		UsuarioPessoa pessoa = daoGeneric.pesquisar2(19L, UsuarioPessoa.class);

		daoGeneric.deletarPorId(pessoa);

	}

	@Test
	public void testeConsultar() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.listar(UsuarioPessoa.class);

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}

	}

	@Test
	public void testQueryList() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.getEntityManager().createQuery(" from UsuarioPessoa where nome = 'Eder'")
				.getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}

	}

	@Test
	public void testQueryListMaxResult() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();

		List<UsuarioPessoa> list = daoGeneric.
				getEntityManager().
				createQuery(" from UsuarioPessoa order by id").
				setMaxResults(3).
				getResultList();

		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
	}
	
	@Test
	public void testeQueryListParameter() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createQuery(" from UsuarioPessoa where nome = :nome").
				setParameter("nome", "Eder").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
		
	}
	
	@Test
	public void testeQuerySomaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		Long somaIdade = (Long) daoGeneric.getEntityManager().
				createQuery("select sum(u.idade) from UsuarioPessoa u ").
				getSingleResult();
		
		System.out.println("Soma das Idades = " + somaIdade);
	}	
	
	@Test
	public void testeQueryMediaIdade() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		
		Double somaIdade = (Double) daoGeneric.getEntityManager().
				createQuery("select avg(u.idade) from UsuarioPessoa u ").
				getSingleResult();
		
		System.out.println("Média das Idades = " + somaIdade);
	}	
	
	@Test
	public void testeNamedQuery1() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createNamedQuery("UsuarioPessoa.findAll").getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
		
	}
	
	@Test
	public void testeNamedQuery2() {
		DaoGeneric<UsuarioPessoa> daoGeneric = new DaoGeneric<UsuarioPessoa>();
		List<UsuarioPessoa> list = daoGeneric.getEntityManager().
				createNamedQuery("UsuarioPessoa.findByName").
				setParameter("nome", "Eder").
				getResultList();
		
		for (UsuarioPessoa usuarioPessoa : list) {
			System.out.println(usuarioPessoa);
			System.out.println("-------------------------");
		}
		
	}	
	
	@Test
	public void testSetPhone() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar2(22L, UsuarioPessoa.class);
		
		TelephoneUser telephoneUser = new TelephoneUser();
		telephoneUser.setType("Work");
		telephoneUser.setNumber("6122224455");
		telephoneUser.setUsuarioPessoa(pessoa);
		
		daoGeneric.salvar(telephoneUser);
		
	}
	
	@Test
	public void testFindPhones() {
		DaoGeneric daoGeneric = new DaoGeneric();
		
		UsuarioPessoa pessoa = (UsuarioPessoa) daoGeneric.pesquisar2(21L, UsuarioPessoa.class);
		
		for (TelephoneUser phone : pessoa.getTelephoneUsers()) {
			System.out.println(phone.getUsuarioPessoa().getNome());			
			System.out.println(phone.getType());
			System.out.println(phone.getNumber());
			System.out.println("-------------------------");
		}
		
	}	

}

















