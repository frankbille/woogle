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
package wicket.contrib.woogle.components;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.nutch.parse.ParseData;
import org.apache.nutch.searcher.Hit;
import org.apache.nutch.searcher.HitDetails;
import org.apache.nutch.searcher.Hits;
import org.apache.nutch.searcher.NutchBean;
import org.apache.nutch.searcher.Query;

import wicket.PageParameters;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.markup.html.AjaxLink;
import wicket.behavior.SimpleAttributeModifier;
import wicket.contrib.woogle.WoogleApplication;
import wicket.contrib.woogle.domain.Site;
import wicket.contrib.woogle.pages.SearchPage;
import wicket.extensions.markup.html.repeater.RepeatingView;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.link.ExternalLink;
import wicket.markup.html.list.Loop;
import wicket.markup.html.panel.Panel;

public class SearchResult extends Panel {
	private static final long serialVersionUID = 1L;

	/** Logging */
	private static final Log log = LogFactory.getLog(SearchResult.class);

	private Hits hits;

	private Query query;
	
	private int calcPage;

	public SearchResult(String id, final String searchString) {
		this(id, searchString, null, null, 0);
	}

	public SearchResult(String id, final String searchString, Hits hits, Query query,
			final int offset) {
		super(id);

		final NutchBean nutch = WoogleApplication.get().getNutch();

		if (hits == null) {
			try {
				query = Query.parse(searchString);

				hits = nutch.search(query, 100000);
			} catch (Exception e) {
				log.debug("Failed to search", e);
			}
		}
		this.hits = hits;
		this.query = query;

		// Trackback
		PageParameters parameters = new PageParameters();
		parameters.add("search", searchString);
		add(new BookmarkablePageLink("trackback", SearchPage.class, parameters));
		
		
		// Stats
		String stats = "Showing "+(offset+1)+"-"+(offset+10)+" of "+hits.getLength();
		add(new Label("stats", stats));
		
		// Result
		RepeatingView hitsView = new RepeatingView("hits");
		add(hitsView);

		// Preload the sites
		List<Site> sites = WoogleApplication.get().getSiteDAO().listActive();
		
		for (Site site : sites) {
			Pattern pat = Pattern.compile(site.getMatchPattern());
			site.setPattern(pat);
		}
		
		for (int i = offset; offset + 10 > i && i < hits.getLength(); i++) {
			Hit hit = hits.getHit(i);

			try {
				final HitDetails details = nutch.getDetails(hit);
				ParseData data = nutch.getParseData(details);
				String summary = nutch.getSummary(details, query);
				final String url = details.getValue("url");

				// Container
				final WebMarkupContainer hitContainer = new WebMarkupContainer("" + i);
				hitsView.add(hitContainer);
				
				ExternalLink item = new ExternalLink("hitLink", url);
				hitContainer.add(item);

				String cssClass = "";

				// Type of link
				for (Site site : sites) {
					Matcher mat = site.getPattern().matcher(url);
					if (mat.matches()) {
						cssClass += site.getCssClass();
						break;
					}
				}

				// Css class
				item.add(new SimpleAttributeModifier("class", cssClass));

				// Title
				item.add(new Label("title", data.getTitle()));

				// Summary
				item.add(new Label("summary", summary)
						.setEscapeModelStrings(false));

				// Link
				item.add(new Label("linkLabel", url));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		// Paging
		int numOfPages = hits.getLength() / 10;
		final int currentPage = offset / 10;
		int numLoopPages = numOfPages > 9 ? 9 : numOfPages;
		calcPage = 0;		
		
		if (currentPage > 4) {
			calcPage = currentPage - 4;
		}
		
		add(new Loop("pages", numLoopPages) {
			private static final long serialVersionUID = 1L;
			
			@Override
			protected void populateItem(final LoopItem item) {
				final int newOffset = calcPage * 10;
				
//				if (newOffset != offset) {
					AjaxLink link = new AjaxLink("pageLink") {
						private static final long serialVersionUID = 1L;
	
						@Override
						public void onClick(AjaxRequestTarget target) {
							SearchResult result = new SearchResult(SearchResult.this.getId(), searchString, getHits(), getQuery(), newOffset);
							
							SearchResult.this.getParent().replace(result);
							
							target.addComponent(result);
						}
					};
					item.add(link);
					
					link.add(new Label("pageLabel", ""+(calcPage+1)));
//				} else {
					
//				}
				
				calcPage++;
			}
		});
	}

	public Hits getHits() {
		return hits;
	}

	public Query getQuery() {
		return query;
	}

}
