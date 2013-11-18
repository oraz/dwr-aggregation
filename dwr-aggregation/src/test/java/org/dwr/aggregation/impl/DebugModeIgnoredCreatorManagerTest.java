package org.dwr.aggregation.impl;

import org.directwebremoting.extend.Creator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DebugModeIgnoredCreatorManagerTest {
    private final DebugModeIgnoredCreatorManager manager = new DebugModeIgnoredCreatorManager();

    @DataProvider
    public Object[][] debugModes() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    @Test
    public void testGetCreatorNamesIncludingHiddenWhenDebugModeIsOn() {
        final String firstName = "first.js";
        final String secondName = "second.js";
        final String thirdName = "third.js";
        final Map<String, Creator> creators = givenCreators(firstName, secondName, thirdName);
        manager.setDebug(true);
        manager.setCreators(creators);

        final Collection<String> creatorNames = manager.getCreatorNames(true);

        assertThat(creatorNames, containsInAnyOrder(firstName, secondName, thirdName));
    }

    @Test(expectedExceptions = SecurityException.class)
    public void testGetCreatorNamesIncludingHiddenWhenDebugModeIsOff() {
        manager.setDebug(false);
        manager.setCreators(givenCreators("first.js", "second.js"));

        manager.getCreatorNames(true);
    }

    @Test(dataProvider = "debugModes")
    public void testGetCreatorNamesExcludingHidden(final boolean debug) {
        final String firstName = "first.js";
        final String secondName = "second.js";
        final Map<String, Creator> creators = givenCreators(firstName, secondName);
        final String thirdName = "third.js";
        final Creator hidden = mock(Creator.class);
        when(hidden.isHidden()).thenReturn(true);
        creators.put(thirdName, hidden);

        manager.setDebug(debug);
        manager.setCreators(creators);

        final Collection<String> creatorNames = manager.getCreatorNames(false);

        assertThat(creatorNames, containsInAnyOrder(firstName, secondName));
    }

    private Map<String, Creator> givenCreators(final String... names) {
        final Map<String, Creator> creators = new HashMap<String, Creator>();
        for (final String each : names) {
            creators.put(each, mock(Creator.class));
        }
        return creators;
    }
}
