package org.dwr.aggregation.impl.mock;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.servlet.CachingHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.directwebremoting.servlet.PathConstants.EXTENSION_JS;

public class MockInterfaceHandler extends CachingHandler {
    private final String contextPath;
    private final String servletPath;
    private final String interfaceHandlerUrl;
    private final Map<String, String> modules;

    public MockInterfaceHandler(final String contextPath,
                                final String servletPath,
                                final String interfaceHandlerUrl) {
        this.contextPath = contextPath;
        this.servletPath = servletPath;
        this.interfaceHandlerUrl = interfaceHandlerUrl;
        modules = new ListOrderedMap();
    }

    @Override
    protected long getLastModifiedTime() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String generateCachableContent(final String contextPath,
                                          final String servletPath,
                                          final String pathInfo) throws IOException {
        if (StringUtils.equals(this.contextPath, contextPath) &&
                StringUtils.equals(this.servletPath, servletPath) &&
                modules.containsKey(pathInfo)) {
            return modules.get(pathInfo);
        }
        return EMPTY;
    }

    protected void add(final String... moduleNames) {
        for (final String name : moduleNames) {
            final String content = format("[START]%s.js content %s [END]", name, randomAlphabetic(10));
            modules.put(interfaceHandlerUrl + name + EXTENSION_JS, content);
        }
    }

    public Collection<String> getModuleContents() {
        return modules.values();
    }
}
