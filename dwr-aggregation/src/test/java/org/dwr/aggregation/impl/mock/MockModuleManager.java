package org.dwr.aggregation.impl.mock;

import org.directwebremoting.extend.Module;
import org.directwebremoting.extend.ModuleManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class MockModuleManager implements ModuleManager {
    private final MockInterfaceHandler mockInterfaceHandler;
    private final List<String> modules;

    public MockModuleManager(final MockInterfaceHandler mockInterfaceHandler) {
        this.mockInterfaceHandler = mockInterfaceHandler;
        modules = new ArrayList<String>();
    }

    public Collection<String> getModuleNames(final boolean includeHidden) {
        return includeHidden ? Collections.<String>emptyList() : unmodifiableList(modules);
    }

    public Module getModule(final String name, final boolean includeHidden) {
        throw new RuntimeException("Not implemented exception");
    }

    public void add(final String... modules) {
        mockInterfaceHandler.add(modules);
        this.modules.addAll(asList(modules));
    }
}
