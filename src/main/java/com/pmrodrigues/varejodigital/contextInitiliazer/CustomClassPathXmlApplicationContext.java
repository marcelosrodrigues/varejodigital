package com.pmrodrigues.varejodigital.contextInitiliazer;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * Created by Marceloo on 05/02/2015.
 */
public class CustomClassPathXmlApplicationContext extends XmlWebApplicationContext {

    @Override
    protected void initBeanDefinitionReader(final XmlBeanDefinitionReader reader) {
        reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
        reader.setNamespaceAware(true);
    }
}
