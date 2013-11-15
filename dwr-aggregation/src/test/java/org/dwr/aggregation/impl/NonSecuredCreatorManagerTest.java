package org.dwr.aggregation.impl;

import org.directwebremoting.extend.Creator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;

public class NonSecuredCreatorManagerTest {
    private final NonSecuredCreatorManager manager = new NonSecuredCreatorManager();

    @DataProvider
    public Object[][] debugModes() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    @Test(dataProvider = "debugModes")
    public void testGetCreatorNamesWhenDebugModIsOn(final boolean debug) {
        final String firstName = randomAlphabetic(5);
        final String secondName = randomAlphabetic(5);
        final String thirdName = randomAlphabetic(5);
        final Map<String, Creator> creators = givenCreators(firstName, secondName, thirdName);

        manager.setDebug(debug);
        manager.setCreators(creators);

        final Collection<String> creatorNames = manager.getCreatorNames();

        assertThat(creatorNames, containsInAnyOrder(firstName, secondName, thirdName));
    }

    private Map<String, Creator> givenCreators(final String... names) {
        final Map<String, Creator> creators = new HashMap<String, Creator>();
        for (final String each : names) {
            creators.put(each, mock(Creator.class));
        }
        return creators;
    }
}
