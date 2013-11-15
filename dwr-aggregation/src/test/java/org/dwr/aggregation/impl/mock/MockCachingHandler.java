package org.dwr.aggregation.impl.mock;

import org.apache.commons.lang3.StringUtils;
import org.directwebremoting.servlet.CachingHandler;

import java.io.IOException;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class MockCachingHandler extends CachingHandler {
    private final String contextPath;
    private final String servletPath;
    private final String pathInfo;
    private final String content;

    public MockCachingHandler(final String contextPath, final String servletPath, final String pathInfo) {
        this.contextPath = contextPath;
        this.servletPath = servletPath;
        this.pathInfo = pathInfo;
        content = format("[START] %s context %s [END]", pathInfo, randomAlphabetic(10));
    }

    @Override
    protected long getLastModifiedTime() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public String generateCachableContent(final String contextPath, final String servletPath, final String pathInfo) throws IOException {
        if (StringUtils.equals(this.contextPath, contextPath) &&
                StringUtils.equals(this.servletPath, servletPath) &&
                StringUtils.equals(this.pathInfo, pathInfo)) {
            return content;
        }
        return EMPTY;
    }

    public String getContent() {
        return content;
    }
}
