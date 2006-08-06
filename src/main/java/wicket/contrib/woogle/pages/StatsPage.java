package wicket.contrib.woogle.pages;

import java.util.List;

import wicket.contrib.woogle.WoogleApplication;
import wicket.contrib.woogle.components.ProgressBar;
import wicket.contrib.woogle.dao.SearchDAO;
import wicket.contrib.woogle.domain.Search;
import wicket.markup.html.basic.Label;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class StatsPage extends WoogleBasePage {
	private static final long serialVersionUID = 1L;

	public StatsPage() {
		SearchDAO searchDAO = WoogleApplication.get().getSearchDAO();
		List<Search> topSearches = searchDAO.listTopSearches();
		
		
		add(new ListView("searches", topSearches) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				Search search = (Search) item.getModelObject();
				
				item.add(new Label("pos", ""+(item.getIndex()+1)+"."));
				item.add(new Label("search", search.getSearch()));
				item.add(new Label("pct", "" + search.getPct() + "%"));
				item.add(new ProgressBar("bar", search.getPct()));
			}
		});
	}
}
