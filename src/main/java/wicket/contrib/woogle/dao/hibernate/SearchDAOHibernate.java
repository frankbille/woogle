package wicket.contrib.woogle.dao.hibernate;

import java.util.Date;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wicket.contrib.woogle.dao.SearchDAO;
import wicket.contrib.woogle.domain.Search;

public class SearchDAOHibernate extends HibernateDaoSupport implements
		SearchDAO {

	public void save(Search search) {
		if (search.getSearchTime() == null) {
			search.setSearchTime(new Date());
		}
		
		getHibernateTemplate().save(search);
	}

}
