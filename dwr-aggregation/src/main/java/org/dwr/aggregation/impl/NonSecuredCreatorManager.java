package org.dwr.aggregation.impl;

import org.directwebremoting.extend.Creator;
import org.directwebremoting.impl.DefaultCreatorManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class NonSecuredCreatorManager extends DefaultCreatorManager {
    @Override
    public Collection<String> getCreatorNames(final boolean includeHidden) throws SecurityException {
        if (includeHidden) {
            return super.getCreatorNames(includeHidden);
        } else {
            final Collection<String> names = new HashSet<String>();
            for (final Map.Entry<String, Creator> each : creators.entrySet()) {
                if (!each.getValue().isHidden()) {
                    names.add(each.getKey());
                }
            }
            return names;
        }
    }
}
