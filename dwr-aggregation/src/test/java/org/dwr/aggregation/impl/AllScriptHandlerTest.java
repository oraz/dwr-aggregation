package org.dwr.aggregation.impl;

import org.directwebremoting.extend.ModuleManager;
import org.directwebremoting.impl.DefaultContainer;
import org.directwebremoting.servlet.CachingHandler;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.join;
import static org.directwebremoting.servlet.PathConstants.EXTENSION_JS;
import static org.directwebremoting.servlet.PathConstants.PATH_PREFIX;
import static org.directwebremoting.util.MimeConstants.MIME_JS;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class AllScriptHandlerTest {
    private AllScriptHandler handler;
    @Mock
    private ModuleManager moduleManager;
    private String interfaceHandlerUrl;
    @Mock
    private CachingHandler interfaceHandler;
    private String engineHandlerUrl;
    @Mock
    private CachingHandler engineHandler;
    private String dtoAllHandlerUrl;
    @Mock
    private CachingHandler dtoAllHandler;

    @BeforeMethod
    public void setUp() {
        initMocks(this);

        final DefaultContainer container = new DefaultContainer();
        container.addParameter("moduleManager", moduleManager);

        interfaceHandlerUrl = withRandomPrefix("/interface/");
        container.addParameter("interfaceHandlerUrl", interfaceHandlerUrl);
        container.addParameter(PATH_PREFIX + interfaceHandlerUrl, interfaceHandler);

        engineHandlerUrl = withRandomPrefix("/engine.js");
        container.addParameter("engineHandlerUrl", engineHandlerUrl);
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
        final String contextPath = randomAlphabetic(10);
        final String servletPath = randomAlphabetic(10);
        final String pathInfo = randomAlphabetic(10);
        final String engineJSContent = setUpEngineJS(contextPath, servletPath);

        final String result = handler.generateCachableContent(contextPath, servletPath, pathInfo);

        assertEquals(result, engineJSContent + "\n");
    }

    @Test
    public void testGenerateContentWhenThereAreModules() throws IOException {
        final String contextPath = randomAlphabetic(10);
        final String servletPath = randomAlphabetic(10);
        final String pathInfo = randomAlphabetic(10);
        final String engineJSContent = setUpEngineJS(contextPath, servletPath);
        final Collection<String> modules = givenModules(contextPath, servletPath, randomModuleName(), randomModuleName(), randomModuleName());

        final String result = handler.generateCachableContent(contextPath, servletPath, pathInfo);

        assertEquals(result, engineJSContent + "\n" + join(modules, '\n') + "\n");
    }

    private String randomModuleName() {
        return "module" + randomAlphabetic(10);
    }

    private Collection<String> givenModules(final String contextPath, final String servletPath, final String... names) throws IOException {
        when(moduleManager.getModuleNames(false)).thenReturn(asList(names));
        final Collection<String> modules = new ArrayList<String>();
        for (final String each : names) {
            final String moduleContent = format("[START]%s.js content %s [END]", each, randomAlphabetic(10));
            when(interfaceHandler.generateCachableContent(contextPath, servletPath, interfaceHandlerUrl + each + EXTENSION_JS))
                    .thenReturn(moduleContent);
            modules.add(moduleContent);
        }
        return modules;
    }

    private String setUpEngineJS(final String contextPath, final String servletPath) throws IOException {
        final String engineJSContent = "[START]EngineJS content " + randomAlphabetic(10) + " [END]";

        when(engineHandler.generateCachableContent(contextPath, servletPath, engineHandlerUrl)).thenReturn(engineJSContent);
        return engineJSContent;
    }

    private String withRandomPrefix(final String url) {
        return "/" + randomAlphabetic(5) + url;
    }
}
