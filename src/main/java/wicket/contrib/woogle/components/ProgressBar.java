package wicket.contrib.woogle.components;

import wicket.behavior.SimpleAttributeModifier;
import wicket.markup.html.WebMarkupContainer;
import wicket.markup.html.panel.Panel;

public class ProgressBar extends Panel {
	private static final long serialVersionUID = 1L;

	public ProgressBar(String id, int pct) {
		super(id);
		
		WebMarkupContainer gradient = new WebMarkupContainer("gradient");
		add(gradient);
		
		if (pct > 100) {
			pct = 100;
		}
		
		if (pct < 0) {
			pct = 0;
		}
		
		gradient.add(new SimpleAttributeModifier("style", "width: "+pct+"%;"));
	}

}
