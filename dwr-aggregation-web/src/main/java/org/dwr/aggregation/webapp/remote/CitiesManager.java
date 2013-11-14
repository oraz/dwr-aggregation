package org.dwr.aggregation.webapp.remote;

import java.util.Collection;

import static java.util.Arrays.asList;

public class CitiesManager {
    public Collection<String> getAllCities() {
        return asList("Paris", "London");
    }
}
