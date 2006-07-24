package wicket.contrib.woogle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wicket.IRequestTarget;
import wicket.PageParameters;
import wicket.contrib.woogle.pages.SearchPage;
import wicket.request.RequestParameters;
import wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import wicket.request.target.component.BookmarkablePageRequestTarget;
import wicket.request.target.component.PageRequestTarget;

public class SearchUrlCodingStrategy implements IRequestTargetUrlCodingStrategy {

	public IRequestTarget decode(RequestParameters requestParameters) {
		IRequestTarget target = null;

		String path = requestParameters.getPath();

		Pattern pat = Pattern.compile("^\\/q\\/(.+)$");
		Matcher mat = pat.matcher(path);
		if (mat.matches()) {
			String search = mat.group(1);

			PageParameters parameters = new PageParameters();
			parameters.add("search", search);
			
			SearchPage page = new SearchPage(parameters);
			
			target = new PageRequestTarget(page);
		}

		return target;
	}

	public CharSequence encode(IRequestTarget requestTarget) {
		StringBuffer buffer = new StringBuffer();

		if (requestTarget instanceof BookmarkablePageRequestTarget) {
			BookmarkablePageRequestTarget target = (BookmarkablePageRequestTarget) requestTarget;

			PageParameters pageParameters = target.getPageParameters();
			String search = pageParameters.getString("search");
			
			search = search.replace("/", "%2F");
			search = search.replace("?", "%3F");
			search = search.replace("\"", "%22");
			search = search.replace("'", "%27");
			search = search.replace("`", "%60");
			
			buffer.append("/q/");
			buffer.append(search);
		}

		return buffer.toString();
	}

	public boolean matches(IRequestTarget requestTarget) {
		boolean matches = false;

		if (requestTarget instanceof BookmarkablePageRequestTarget) {
			BookmarkablePageRequestTarget target = (BookmarkablePageRequestTarget) requestTarget;

			PageParameters pp = target.getPageParameters();
			
			if (SearchPage.class.equals(target.getPageClass())) {
				if (pp.getString("search") != null) {
					matches = true;
				}
			}
		}

		return matches;
	}

}
