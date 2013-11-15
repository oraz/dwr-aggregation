package org.dwr.aggregation.webapp.tests;

import org.dwr.aggregation.webapp.tests.pages.MainPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.dwr.aggregation.webapp.tests.pages.MainPage.citiesList;
import static org.dwr.aggregation.webapp.tests.pages.MainPage.userList;
import static org.hamcrest.Matchers.contains;

public class MainPageTest extends AbstractSeleniumTests {
    @DataProvider
    public Object[][] pageUrls() {
        return new Object[][]{
                {"/html/notAggregatedServices.html"},
//                {"/html/aggregatedServices.html"}
        };
    }

    @Test(groups = Acceptance, dataProvider = "pageUrls")
    public void testCheckPage(final String pageUrl) {
        getPage(pageUrl, MainPage.class)
                .andNow(userList(contains("ann", "john")))

                .andNow(citiesList(contains("Paris", "London")));
    }
}
