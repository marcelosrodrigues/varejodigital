package test.com.pmrodrigues.varejodigital.webservice.adapters;


import com.pmrodrigues.varejodigital.webservice.adapters.DateTypeAdapter;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Marceloo on 16/04/2015.
 */
public class TestDateTypeAdapter {

    private DateTypeAdapter adapter = new DateTypeAdapter();

    @Test
    public void testUnmarshal() throws Exception {

        Date date = adapter.unmarshal("16/04/2015");
        Date expected = new DateTime(2015, 4, 16, 0, 0, 0).toDate();
        assertEquals(expected, date);

        date = adapter.unmarshal("16-04-2015");
        assertEquals(expected, date);

        date = adapter.unmarshal("2015-04-16");
        assertEquals(expected, date);

        date = adapter.unmarshal("04-2015");
        expected = new DateTime(2015, 4, 1, 0, 0, 0).toDate();
        assertEquals(expected, date);
    }

    @Test
    public void testNaoPodeConverterDataInvalida() throws Exception {
        Date date = adapter.unmarshal("abcd");
        assertNull(date);
    }

    @Test
    public void testMarshal() throws Exception {

        Date date = new DateTime(2015, 04, 16, 0, 0, 0).toDate();
        String formatter = adapter.marshal(date);

        assertEquals("2015-04-16", formatter);
    }
}