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

import wicket.markup.html.WebPage;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.link.Link;
import wicket.protocol.http.WebResponse;

public abstract class WoogleBasePage extends WebPage {

	public WoogleBasePage() {
		// Front page link
		Link searchLink = new BookmarkablePageLink("woogle", SearchPage.class);
		add(searchLink);
		
		// Menus
		add(new BookmarkablePageLink("faqLink", FaqPage.class));
		add(new BookmarkablePageLink("addSiteLink", AddSitePage.class));
		add(new BookmarkablePageLink("statsLink", StatsPage.class));
	}

	@Override
	protected void configureResponse() {
		super.configureResponse();
		
		final WebResponse response = getWebRequestCycle().getWebResponse();
		
		response.setCharacterEncoding("iso-8859-1");
		response.setContentType("text/html");
	}
	
	
}
