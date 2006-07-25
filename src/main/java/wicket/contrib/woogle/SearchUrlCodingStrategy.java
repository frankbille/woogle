package wicket.contrib.woogle;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wicket.IRequestTarget;
import wicket.PageParameters;
import wicket.contrib.woogle.pages.SearchPage;
import wicket.request.RequestParameters;
import wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import wicket.request.target.component.BookmarkablePageRequestTarget;
import wicket.request.target.component.PageRequestTarget;

public class SearchUrlCodingStrategy implements IRequestTargetUrlCodingStrategy {

	private static final Log log = LogFactory
			.getLog(SearchUrlCodingStrategy.class);

	public IRequestTarget decode(RequestParameters requestParameters) {
		IRequestTarget target = null;

		String path = requestParameters.getPath();

		Pattern pat = Pattern.compile("^\\/q\\/(.+)$");
		Matcher mat = pat.matcher(path);
		if (mat.matches()) {
			String search = mat.group(1);

			// try {
			// System.out.println(search);
			// search = URLDecoder.decode(search, "UTF-8");
			// System.out.println(search);
			// } catch (UnsupportedEncodingException e) {
			// log.fatal("Choose a different encoding", e);
			// }

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
			buffer.append("/q/");

			try {
				String[] pierces = search.split(" ");

				for (String pierce : pierces) {
					if (buffer.length() > 3) {
						buffer.append("%20");
					}

					buffer.append(URLEncoder.encode(pierce, "UTF-8"));
				}
			} catch (UnsupportedEncodingException e) {
				log.fatal("Choose a different encoding", e);
			}
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
