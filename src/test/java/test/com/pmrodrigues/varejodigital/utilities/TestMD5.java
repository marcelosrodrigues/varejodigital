package test.com.pmrodrigues.varejodigital.utilities;

import com.pmrodrigues.varejodigital.utilities.MD5;
import org.junit.Assert;
import org.junit.Test;

public class TestMD5 {

    @Test
    public void encrypt() throws Exception {
        Assert.assertEquals("25d55ad283aa400af464c76d713c07ad", MD5.encrypt("12345678"));
    }
}
