package wicket.contrib.woogle.dao.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
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
		// Get total number of searches
		String hql = "SELECT COUNT(s) FROM Search s";
		
		int countResult = (Integer) getHibernateTemplate().find(hql).get(0);
				
		hql = "SELECT s, COUNT(s.search) FROM Search s GROUP BY s.search ORDER BY COUNT(s.search) DESC";
		
		getHibernateTemplate().setMaxResults(10);
		
		List result = getHibernateTemplate().find(hql);
		List<Search> searches = new ArrayList<Search>();
		for (Iterator iter = result.iterator(); iter.hasNext();) {
			Object[] element = (Object[]) iter.next();
			
			Search search = (Search) element[0];
			int searchCount = (Integer) element[1];
			double pct = (double)searchCount/(double)countResult;
			search.setPct((int) Math.round(pct*100));
			searches.add(search);
		}
		
		return searches;
	}

}
