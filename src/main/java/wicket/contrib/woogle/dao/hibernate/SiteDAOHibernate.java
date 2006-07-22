/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.woogle.dao.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import wicket.contrib.woogle.dao.SiteDAO;
import wicket.contrib.woogle.domain.Site;

@Transactional(propagation = Propagation.REQUIRED)
public class SiteDAOHibernate extends HibernateDaoSupport implements SiteDAO {

	@Transactional(readOnly = true)
	public Site get(Long id) {
		return (Site) getHibernateTemplate().load(Site.class, id);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Site> listActive() {
		DetachedCriteria c = DetachedCriteria.forClass(Site.class);
		c.add(Restrictions.eq("active", true));
		
		return getHibernateTemplate().findByCriteria(c);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Site> listAll() {
		DetachedCriteria c = DetachedCriteria.forClass(Site.class);
		c.add(Restrictions.eq("active", true));
		
		return getHibernateTemplate().findByCriteria(c);
	}

	public void save(Site site) {
		getHibernateTemplate().saveOrUpdate(site);
	}

}
