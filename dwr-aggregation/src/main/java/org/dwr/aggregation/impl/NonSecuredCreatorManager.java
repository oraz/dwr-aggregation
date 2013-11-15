package org.dwr.aggregation.impl;

import org.directwebremoting.impl.DefaultCreatorManager;

import java.util.Collection;

import static java.util.Collections.unmodifiableSet;

public class NonSecuredCreatorManager extends DefaultCreatorManager {
    @Override
    public Collection<String> getCreatorNames() throws SecurityException {
        return unmodifiableSet(creators.keySet());
    }
}
