package org.dwr.aggregation.impl;

import org.directwebremoting.Container;
import org.directwebremoting.extend.ModuleManager;
import org.directwebremoting.impl.DefaultContainer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.directwebremoting.util.MimeConstants.MIME_JS;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

public class AllScriptHandlerTest {
    private AllScriptHandler handler;
    private ModuleManager moduleManager;
    private String interfaceHandlerUrl;
    private String engineHandlerUrl;
    private String dtoAllHandlerUrl;

    @BeforeMethod
    public void setUp() throws Exception {
        handler = new AllScriptHandler();
        moduleManager = mock(ModuleManager.class);
        handler.setModuleManager(moduleManager);
        interfaceHandlerUrl = "/interface" + randomAlphabetic(5) + "/";
        handler.setInterfaceHandlerUrl(interfaceHandlerUrl);
        engineHandlerUrl = "/engine.js" + randomAlphabetic(5);
        handler.setEngineHandlerUrl(engineHandlerUrl);
        dtoAllHandlerUrl = "/dtoAll.js" + randomAlphabetic(5);
        handler.setDtoAllHandlerUrl(dtoAllHandlerUrl);
        final DefaultContainer container = new DefaultContainer();
        handler.afterContainerSetup(container);
    }

    @Test
    public void testMimeTypeMustBeJavaScript() {
        assertEquals(handler.getMimeType(), MIME_JS);
    }
}
