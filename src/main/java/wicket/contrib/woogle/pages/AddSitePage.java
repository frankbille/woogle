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

import java.util.regex.Pattern;

import wicket.contrib.woogle.WoogleApplication;
import wicket.contrib.woogle.domain.Site;
import wicket.markup.html.WebComponent;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.basic.Label;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.form.validation.PatternValidator;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;

public class AddSitePage extends WoogleBasePage {
	private static final long serialVersionUID = 1L;

	public AddSitePage() {
		this(null);
	}
	
	public AddSitePage(String url) {
		// URL send
		if (url != null) {
			WebMarkupContainer urlSend = new WebMarkupContainer("urlSend");
			add(urlSend);
			
			urlSend.add(new Label("url", url));
		} else {
			add(new WebComponent("urlSend").setVisible(false));
		}
		
		
		// Form
		final Site site = new Site();
		Form form = new Form("form", new CompoundPropertyModel(site));
		add(form);
		
		TextField urlField = new TextField("url");
		urlField.setRequired(true);
		urlField.add(new PatternValidator(Pattern.compile("^http:\\/\\/.*$")));
		form.add(urlField);
		
		form.add(new Button("send", new Model("Send")) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit() {
				WoogleApplication.get().getSiteDAO().save(site);
				
				setResponsePage(new AddSitePage(site.getUrl()));
			}
		});
	}
}
