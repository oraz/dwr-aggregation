package org.dwr.aggregation.impl;

import org.directwebremoting.impl.DefaultContainer;
import org.directwebremoting.servlet.CachingHandler;
import org.dwr.aggregation.impl.mock.MockCachingHandler;
import org.dwr.aggregation.impl.mock.MockInterfaceHandler;
import org.dwr.aggregation.impl.mock.MockModuleManager;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collection;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.join;
import static org.directwebremoting.servlet.PathConstants.PATH_PREFIX;
import static org.directwebremoting.util.MimeConstants.MIME_JS;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class AllScriptHandlerTest {
    private AllScriptHandler handler;
    private MockModuleManager moduleManager;
    private MockInterfaceHandler interfaceHandler;
    private MockCachingHandler engineHandler;
    private String dtoAllHandlerUrl;
    @Mock
    private CachingHandler dtoAllHandler;
    private String contextPath = randomAlphabetic(10);
    private String servletPath = randomAlphabetic(10);

    @BeforeMethod
    public void setUp() {
        initMocks(this);

        contextPath = randomAlphabetic(10);
        servletPath = randomAlphabetic(10);

        final DefaultContainer container = new DefaultContainer();

        final String interfaceHandlerUrl = withRandomPrefix("/interface/");
        container.addParameter("interfaceHandlerUrl", interfaceHandlerUrl);
        interfaceHandler = new MockInterfaceHandler(contextPath, servletPath, interfaceHandlerUrl);
        container.addParameter(PATH_PREFIX + interfaceHandlerUrl, interfaceHandler);

        moduleManager = new MockModuleManager(interfaceHandler);
        container.addParameter("moduleManager", moduleManager);

        final String engineHandlerUrl = withRandomPrefix("/engine.js");
        container.addParameter("engineHandlerUrl", engineHandlerUrl);
        engineHandler = new MockCachingHandler(contextPath, servletPath, engineHandlerUrl);
        container.addParameter(PATH_PREFIX + engineHandlerUrl, engineHandler);

        dtoAllHandlerUrl = withRandomPrefix("/dtoAll.js");
        container.addParameter("dtoAllHandlerUrl", dtoAllHandlerUrl);
        container.addParameter(PATH_PREFIX + dtoAllHandlerUrl, dtoAllHandler);

        container.addParameter("/dwr-aggregation.js", AllScriptHandler.class.getName());
        container.setupFinished();

        handler = (AllScriptHandler) container.getBean("/dwr-aggregation.js");
    }

    @Test
    public void testMimeTypeMustBeJavaScript() {
        assertEquals(handler.getMimeType(), MIME_JS);
    }

    @Test
    public void testGenerateContent() throws IOException {
        final String result = handler.generateCachableContent(contextPath, servletPath, "/dwr-aggregation.js");

        assertEquals(result, engineJS() + "\n");
    }

    @Test
    public void testGenerateContentWhenThereAreModules() throws IOException {
        moduleManager.add(randomModuleName(), randomModuleName(), randomModuleName());

        final String result = handler.generateCachableContent(contextPath, servletPath, "/dwr-aggregation.js");

        assertEquals(result, engineJS() + "\n" + join(allModules(), '\n') + "\n");
    }

    private String randomModuleName() {
        return "module" + randomAlphabetic(10);
    }

    private String engineJS() {
        return engineHandler.getContent();
    }

    private Collection<String> allModules() {
        return interfaceHandler.getModuleContents();
    }

    private String withRandomPrefix(final String url) {
        return "/" + randomAlphabetic(5) + url;
    }
}
