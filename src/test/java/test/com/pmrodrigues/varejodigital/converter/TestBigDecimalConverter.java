package test.com.pmrodrigues.varejodigital.converter;

import com.pmrodrigues.varejodigital.converters.BigDecimalConverter;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBigDecimalConverter {


    @Test
    public void deveConverter() {

        BigDecimalConverter converter = new BigDecimalConverter();
        BigDecimal convertido = converter.convert("10,00", BigDecimal.class, null);
        assertNotNull(convertido);
        assertEquals(BigDecimal.TEN, convertido);

    }

    @Test
    public void deveConverterCommCentavos() {
        BigDecimalConverter converter = new BigDecimalConverter();
        BigDecimal convertido = converter.convert("10,53", BigDecimal.class, null);
        assertNotNull(convertido);
        assertEquals(new BigDecimal("10.53000"), convertido.round(MathContext.DECIMAL32));
    }

}