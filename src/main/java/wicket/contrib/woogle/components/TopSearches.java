package wicket.contrib.woogle.components;

import java.util.List;

import wicket.PageParameters;
import wicket.contrib.woogle.WoogleApplication;
import wicket.contrib.woogle.dao.SearchDAO;
import wicket.contrib.woogle.domain.Search;
import wicket.contrib.woogle.pages.SearchPage;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.link.Link;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;
import wicket.markup.html.panel.Panel;

public class TopSearches extends Panel {
	private static final long serialVersionUID = 1L;

	public TopSearches(String id) {
		super(id);

		SearchDAO searchDAO = WoogleApplication.get().getSearchDAO();
		List<Search> topSearches = searchDAO.listTopSearches();
		
		add(new ListView("searches", topSearches) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				Search search = (Search) item.getModelObject();
				
				PageParameters pp = new PageParameters();
				pp.add("search", search.getSearch());
				
				Link link = new BookmarkablePageLink("link", SearchPage.class, pp);
				item.add(link);
				
				link.add(new Label("search", search.getSearch()));
			}
		});
	}
}
