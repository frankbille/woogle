package wicket.contrib.woogle.domain;

import java.util.Date;

public class Search {
	private Long id;

	private String search;

	private Date searchTime;

	private String sessionId;

	public Search() {
	}

	public Search(String search) {
		this.search = search;
	}

	public Long getId() {
		return id;
	}

	public String getSearch() {
		return search;
	}

	public Date getSearchTime() {
		return searchTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
