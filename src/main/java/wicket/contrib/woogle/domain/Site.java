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
package wicket.contrib.woogle.domain;

import java.util.regex.Pattern;

public class Site {
	private Long id;

	private String title;

	private String url;

	private String matchPattern;

	private String cssClass;

	private boolean active = false;

	private Pattern pattern;

	public String getCssClass() {
		return cssClass;
	}

	public Long getId() {
		return id;
	}

	public String getMatchPattern() {
		return matchPattern;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMatchPattern(String matchPattern) {
		this.matchPattern = matchPattern;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
