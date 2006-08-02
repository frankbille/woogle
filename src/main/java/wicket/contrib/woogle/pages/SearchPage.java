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
package wicket.contrib.woogle.pages;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.PageParameters;
import wicket.contrib.woogle.WoogleApplication;
import wicket.contrib.woogle.WoogleSession;
import wicket.contrib.woogle.components.SearchResult;
import wicket.contrib.woogle.domain.Search;
import wicket.markup.html.WebComponent;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;
import wicket.util.string.StringValueConversionException;
import wicket.util.string.Strings;

public class SearchPage extends WoogleBasePage {
	private static final long serialVersionUID = 1L;
	
	/** Logging */
	private static final Log log = LogFactory.getLog(SearchPage.class);

	public SearchPage(PageParameters parameters) {
		// Check for search criterias in the parameters
		String searchString = parameters.getString("search");
		if (Strings.isEmpty(searchString)) {
			searchString = null;
		}
		
		int page = 0;
		try {
			page = parameters.getInt("page");
		} catch (StringValueConversionException e) {
			// Ok, but we log it.
			log.debug("'page' parameter couldn't be converted to an int", e);
		}

		final Search search = new Search(searchString);

		// Search form
		add(new SearchForm("searchForm", search));

		// Stats
		add(new Label("indexPages", ""+WoogleApplication.get().getIndexPages()).setRenderBodyOnly(true));
		add(new BookmarkablePageLink("addSite", AddSitePage.class));
		
		// Result place holder
		if (searchString == null) {
//			add(new TopSearches("result").setOutputMarkupId(true));
			add(new WebComponent("result").setVisible(false));
		} else {
			// Save the search
			String sessionId = WoogleSession.get().getId();
			search.setSessionId(sessionId);
			search.setSearchTime(new Date());
			WoogleApplication.get().getSearchDAO().save(search);

			
			add(new SearchResult("result", searchString, page).setOutputMarkupId(true));
		}
	}

	private class SearchForm extends Form {
		private static final long serialVersionUID = 1L;

		private Search search;
		
		public SearchForm(String id, Search search) {
			super(id, new CompoundPropertyModel(search));

			this.search = search;
			
			add(new TextField("search"));
			add(new Button("searchButton", new Model("Search")));
		}

		@Override
		protected void onSubmit() {			
			// Save the search
			String sessionId = WoogleSession.get().getId();
			search.setSessionId(sessionId);
			search.setSearchTime(new Date());
			WoogleApplication.get().getSearchDAO().save(search);
			
			// Redirect to search result page.
			// We use page parameters to get a bookmarkable url
			PageParameters parameters = new PageParameters();
			parameters.add("search", search.getSearch());
			setResponsePage(SearchPage.class, parameters);
		}
	}
}
