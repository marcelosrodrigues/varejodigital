package test.com.pmrodrigues.ellasa.utilities;

import org.junit.Assert;
import org.junit.Test;

import com.pmrodrigues.ellasa.utilities.MD5;

public class TestMD5 {

	@Test
	public void encrypt() throws Exception {
		Assert.assertEquals("25d55ad283aa400af464c76d713c07ad",MD5.encrypt("12345678"));
	}
}
