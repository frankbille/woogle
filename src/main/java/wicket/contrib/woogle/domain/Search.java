package wicket.contrib.woogle.domain;

import java.util.Date;

public class Search {
	private Long id;

	private String search;

	private Date searchTime;

	private String ip;

	public Search() {
	}
	
	public Search(String search) {
		this.search = search;
	}
	
	public Long getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public String getSearch() {
		return search;
	}

	public Date getSearchTime() {
		return searchTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setSearchTime(Date searchTime) {
		this.searchTime = searchTime;
	}
}
