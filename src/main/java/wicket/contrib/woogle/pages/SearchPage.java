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

import wicket.ajax.AbstractDefaultAjaxBehavior;
import wicket.ajax.AjaxRequestTarget;
import wicket.ajax.IAjaxCallDecorator;
import wicket.ajax.IAjaxIndicatorAware;
import wicket.ajax.calldecorator.AjaxCallDecorator;
import wicket.ajax.form.AjaxFormSubmitBehavior;
import wicket.behavior.SimpleAttributeModifier;
import wicket.contrib.woogle.components.SearchResult;
import wicket.markup.html.WebComponent;
import wicket.markup.html.form.Button;
import wicket.markup.html.form.Form;
import wicket.markup.html.form.TextField;
import wicket.markup.html.image.Image;
import wicket.model.CompoundPropertyModel;
import wicket.model.Model;

public class SearchPage extends WoogleBasePage {
	private static final long serialVersionUID = 1L;

	private Image indicator;
	
	public SearchPage() {
		this(null);
	}

	public SearchPage(String searchString) {
		final Search search = new Search(searchString);
		
		// Search form
		add(new SearchForm("searchForm", search));
		
		
		// Result place holder
		add(new WebComponent("result").setOutputMarkupId(true));
	}
	
	private class SearchForm extends Form implements IAjaxIndicatorAware {
		private static final long serialVersionUID = 1L;

		public SearchForm(String id, final Search search) {
			super(id, new CompoundPropertyModel(search));
			
			add(new AjaxFormSubmitBehavior(this, "onsubmit") {
				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target) {
					SearchResult result = new SearchResult("result", search.getSearch());
					
					SearchPage.this.replace(result);
					
					target.addComponent(result);
				}

				@Override
				protected IAjaxCallDecorator getAjaxCallDecorator() {
					return new AjaxCallDecorator() {
						private static final long serialVersionUID = 1L;

						@Override
						public CharSequence decorateScript(CharSequence script) {
							return script + " return false";
						}
					};
				}
			});

			add(new TextField("search"));
			add(new Button("searchButton", new Model("Search")));

			// Indicator
			indicator = new Image("indicator", AbstractDefaultAjaxBehavior.INDICATOR);
			indicator.setOutputMarkupId(true);
			indicator.add(new SimpleAttributeModifier("style", "display:none"));
			add(indicator);
		}

		public String getAjaxIndicatorMarkupId() {
			return indicator.getMarkupId();
		}
	}
	
	
	
	private class Search {
		private String search;

		public Search(String search) {
			this.search = search;
		}

		public void setSearch(String search) {
			this.search = search;
		}

		public String getSearch() {
			return search;
		}
	}
}
