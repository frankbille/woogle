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
package wicket.contrib.woogle;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.nutch.searcher.NutchBean;
import org.apache.nutch.searcher.Query;

import wicket.ISessionFactory;
import wicket.Session;
import wicket.contrib.woogle.dao.SearchDAO;
import wicket.contrib.woogle.dao.SiteDAO;
import wicket.contrib.woogle.pages.AddSitePage;
import wicket.contrib.woogle.pages.FaqPage;
import wicket.contrib.woogle.pages.SearchPage;
import wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see wicket.contrib.woogle.Start#main(String[])
 */
public class WoogleApplication extends WebApplication {
	/** Logging */
	private static final Log log = LogFactory.getLog(WoogleApplication.class);
	
	public static WoogleApplication get() {
		return (WoogleApplication) WebApplication.get();
	}
	
	private NutchBean nutch;

	private String nutchDir;
	
	private SiteDAO siteDAO;
	
	private SearchDAO searchDAO;

	/**
	 * @see wicket.Application#getHomePage()
	 */
	public Class getHomePage() {
		return SearchPage.class;
	}

	public NutchBean getNutch() {
		return nutch;
	}

	public String getNutchDir() {
		return nutchDir;
	}

	public SearchDAO getSearchDAO() {
		return searchDAO;
	}

	/**
	 * @see wicket.protocol.http.WebApplication#getSessionFactory()
	 */
	public ISessionFactory getSessionFactory() {
		return new ISessionFactory() {
			public Session newSession() {
				return new WoogleSession(WoogleApplication.this);
			}
		};
	}

	public SiteDAO getSiteDAO() {
		return siteDAO;
	}

	@Override
	protected void init() {
		try {
			log.debug(nutchDir);
			nutch = new NutchBean(new File(nutchDir));
			nutch.search(Query.parse("initialize indexer"), 1);
		} catch (IOException e) {
			log.error("Nutch couldn't be initialized", e);
		}

		
		mountBookmarkablePage("/faq", FaqPage.class);
		mountBookmarkablePage("/add", AddSitePage.class);
		mount("/q", new SearchUrlCodingStrategy());
	}

	public void setNutchDir(String nutchDir) {
		this.nutchDir = nutchDir;
	}
	
	public void setSearchDAO(SearchDAO searchDAO) {
		this.searchDAO = searchDAO;
	}

	public void setSiteDAO(SiteDAO siteDAO) {
		this.siteDAO = siteDAO;
	}
}