package wicket.contrib.woogle;

import java.net.URLEncoder;

import junit.framework.TestCase;

public class TestUrlEncoding extends TestCase {
	public void testEncoding() throws Exception  {
		String src = "\"Me and you!\"";
		
		String[] pierces = src.split(" ");
		
		StringBuffer url = new StringBuffer();
		
		for (String pierce : pierces) {
			if (url.length() > 0) {
				url.append("%20");
			}
			
			url.append(URLEncoder.encode(pierce, "UTF-8"));
		}
		
		System.out.println(url);
	}
}
