package org.dwr.aggregation.webapp.remote;

import java.util.Collection;

import static java.util.Arrays.asList;

public class UsersManager {
    public Collection<String> getAllUsers() {
        return asList("ann", "john");
    }
}
