import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;

import com.google.common.collect.Lists;

public class CcnPostMain {

	public static void main(String[] args) throws Exception {
		HttpPost httpPost = null;

		String text = "Most recent edition";
		String path = "/en/qc/laws/regu/cqlr-c-s-2.1-r-6/latest/cqlr-c-s-2.1-r-6.html";
		
		List<NameValuePair> form = Lists.newArrayList();
		form.add(new BasicNameValuePair("text", text));
		form.add(new BasicNameValuePair("language", "en"));
		form.add(new BasicNameValuePair("searchTitle", "CCN SEARCH"));
		form.add(new BasicNameValuePair("path", path));

		try {
			httpPost = new HttpPost(new URI("http://www.canlii.org/eliisa/highlight.do"));
			httpPost.setEntity(new UrlEncodedFormEntity(form, Charset.forName("UTF-8")));

			CloseableHttpClient httpClient = HttpClients
					.custom()
					.setRedirectStrategy(new LaxRedirectStrategy())
					.disableCookieManagement()
					.build();
			HttpResponse httpResponse = httpClient.execute(httpPost);

			int statusCode = httpResponse.getStatusLine().getStatusCode();
			
			System.out.println(statusCode);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (httpPost != null) {
				httpPost.releaseConnection();
			}
		}
	}
}
