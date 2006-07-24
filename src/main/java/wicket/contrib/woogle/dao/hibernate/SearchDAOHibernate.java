package wicket.contrib.woogle.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import wicket.contrib.woogle.dao.SearchDAO;
import wicket.contrib.woogle.domain.Search;

public class SearchDAOHibernate extends HibernateDaoSupport implements
		SearchDAO {

	public void save(Search search) {
		// Only persist if no other searches found with the same
		// search string and session id.
		DetachedCriteria c = DetachedCriteria.forClass(Search.class);
		c.add(Restrictions.eq("search", search.getSearch()));
		c.add(Restrictions.eq("sessionId", search.getSessionId()));
		
		List result = getHibernateTemplate().findByCriteria(c);
		
		if (result.size() == 0) {
			getHibernateTemplate().save(search);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Search> listTopSearches() {
		String hql = "SELECT s FROM Search s GROUP BY s.search ORDER BY COUNT(s.search) DESC";
		
		getHibernateTemplate().setMaxResults(10);
		
		return getHibernateTemplate().find(hql);
	}

}
