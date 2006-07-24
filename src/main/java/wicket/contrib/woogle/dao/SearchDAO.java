package wicket.contrib.woogle.dao;

import java.util.List;

import wicket.contrib.woogle.domain.Search;

public interface SearchDAO {
	void save(Search search);

	List<Search> listTopSearches();
}
