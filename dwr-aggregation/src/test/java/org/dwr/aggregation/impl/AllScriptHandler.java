package org.dwr.aggregation.impl;

import org.directwebremoting.Container;
import org.directwebremoting.extend.InitializingBean;
import org.directwebremoting.extend.ModuleManager;
import org.directwebremoting.servlet.CachingHandler;

import java.io.IOException;

import static org.directwebremoting.servlet.PathConstants.EXTENSION_JS;
import static org.directwebremoting.servlet.PathConstants.PATH_PREFIX;
import static org.directwebremoting.util.LocalUtil.getSystemClassloadTime;
import static org.directwebremoting.util.MimeConstants.MIME_JS;

public class AllScriptHandler extends CachingHandler implements InitializingBean {
    private ModuleManager moduleManager;
    private String interfaceHandlerUrl;
    private CachingHandler interfaceHandler;
    private CachingHandler dtoAllHandler;
    private CachingHandler engineHandler;
    private String engineHandlerUrl;
    private boolean includeDtoAll;
    private String dtoAllHandlerUrl;

    public AllScriptHandler() {
        setMimeType(MIME_JS);
    }

    @Override
    public String generateCachableContent(final String contextPath, final String servletPath, final String pathInfo) throws IOException {
        final StringBuilder scriptBuilder = new StringBuilder();
        scriptBuilder
                .append(engineHandler.generateCachableContent(contextPath, servletPath, engineHandlerUrl))
                .append('\n');
        if (includeDtoAll) {
            scriptBuilder.append("/*dtoAll*/\n")
                    .append(dtoAllHandler.generateCachableContent(contextPath, servletPath, dtoAllHandlerUrl))
                    .append('\n');
        }
        for (final String each : moduleManager.getModuleNames(false)) {
            scriptBuilder
                    .append(interfaceHandler.generateCachableContent(contextPath, servletPath, interfaceHandlerUrl + each + EXTENSION_JS))
                    .append('\n');
        }
        return scriptBuilder.toString();
    }

    @Override
    protected long getLastModifiedTime() {
        return getSystemClassloadTime();
    }

    public void afterContainerSetup(final Container container) {
        engineHandler = (CachingHandler) container.getBean(PATH_PREFIX + engineHandlerUrl);
        dtoAllHandler = (CachingHandler) container.getBean(PATH_PREFIX + dtoAllHandlerUrl);
        interfaceHandler = (CachingHandler) container.getBean(PATH_PREFIX + interfaceHandlerUrl);
    }

    public void setModuleManager(final ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    public void setInterfaceHandlerUrl(final String interfaceHandlerUrl) {
        this.interfaceHandlerUrl = interfaceHandlerUrl;
    }

    public void setEngineHandlerUrl(final String engineHandlerUrl) {
        this.engineHandlerUrl = engineHandlerUrl;
    }

    public void setGenerateDtoClasses(final String generateDtoClasses) {
        includeDtoAll = generateDtoClasses.matches(".*\\bdtoall\\b.*");
    }

    public void setDtoAllHandlerUrl(final String dtoAllHandlerUrl) {
        this.dtoAllHandlerUrl = dtoAllHandlerUrl;
    }
}

