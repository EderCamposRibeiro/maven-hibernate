package datatablelazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import dao.DaoUser;
import model.UserPerson;

public class LazyDataTableModelUserPerson<T> extends LazyDataModel<UserPerson> {

	private static final long serialVersionUID = 1L;
	
	private DaoUser<UserPerson> dao = new DaoUser<UserPerson>();
	
	public List<UserPerson> list = new ArrayList<UserPerson>();
	
	private String sql = " from UserPerson ";
	
	@Override
	public List<UserPerson> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {

		list = dao.getEntityManager().createQuery(getSql()).
				setFirstResult(first).
				setMaxResults(pageSize).getResultList();
		sql = " from UserPerson ";
		
		setPageSize(pageSize);
		
		Integer numberOfRecords = Integer.parseInt(dao.getEntityManager().createQuery("select count(1)" + getSql()).getSingleResult().toString());
		
		setRowCount(numberOfRecords);
		
		return list;
	}
	
	public String getSql() {
		return sql;
	}
	
	public List<UserPerson> getList() {
		return list;
	}
	
	public void search(String searchField) {
		sql += " where name like '%"+searchField+"%'";
	}

}
