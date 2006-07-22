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

import java.util.List;

import wicket.contrib.woogle.WoogleApplication;
import wicket.contrib.woogle.domain.Site;
import wicket.markup.html.basic.Label;
import wicket.markup.html.link.BookmarkablePageLink;
import wicket.markup.html.link.ExternalLink;
import wicket.markup.html.list.ListItem;
import wicket.markup.html.list.ListView;

public class FaqPage extends WoogleBasePage {
	private static final long serialVersionUID = 1L;
	
	public FaqPage() {
		// Sites
		List<Site> sites = WoogleApplication.get().getSiteDAO().listActive();
		add(new ListView("sites", sites) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem item) {
				Site site = (Site) item.getModelObject();
				
				ExternalLink link = new ExternalLink("link", site.getUrl());
				item.add(link);
				
				link.add(new Label("label", site.getUrl()+" ("+site.getTitle()+")"));
			}
		});
		
		
		// Misc link
		add(new BookmarkablePageLink("addLink", AddSitePage.class));
	}

}
