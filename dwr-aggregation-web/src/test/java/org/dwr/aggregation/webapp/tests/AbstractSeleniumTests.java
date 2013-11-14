package org.dwr.aggregation.webapp.tests;

import org.dwr.aggregation.webapp.tests.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;

public abstract class AbstractSeleniumTests {
    protected final String Acceptance = "acceptance";
    private WebDriver driver;

    @BeforeGroups(groups = Acceptance)
    public final void initDriver() {
        driver = new FirefoxDriver();
    }

    @AfterGroups(groups = Acceptance)
    public final void destroyDriver() {
        driver.quit();
    }

    protected <T extends AbstractPage> T getPage(final String pageUrl, final Class<T> pageClass) {
        final T page;
        try {
            page = pageClass.newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }

        page.init(driver);
        driver.get("http://localhost:8080/aggr" + pageUrl);
        return page;
    }
}
