package test.com.pmrodrigues.ellasa.utilities;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.pmrodrigues.ellasa.utilities.HTMLReader;

public class TestHTMLReader {

	@Test
	public void readBoleto() throws MalformedURLException, IOException,
			SAXException, ParserConfigurationException {

		final HTMLReader reader = new HTMLReader();
		final String html = reader
				.getElement(
						"recibo",
						new URL(
								"https://dev.akatus.com/boleto/YmRiZjI3NTItZDU1Yi00MTQ2LWIzYTctYTZjNzA3ZDkyNGJk.html"));
		assertNotNull(html);
	}
}
