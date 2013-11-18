package org.dwr.aggregation.webapp.tests;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import org.dwr.aggregation.webapp.tests.pages.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public abstract class AbstractSeleniumTests {
    protected final String Acceptance = "acceptance";
    private WebDriver driver;
    private Wait<WebDriver> wait;
    private Properties acceptanceProperties;

    @BeforeGroups(groups = Acceptance)
    public final void initDriver() throws IOException {
        loadAcceptanceProperties();
        driver = htmlUnitDriver();
        final int timeOutInSeconds = parseInt(acceptanceProperties.getProperty("webdriver.timeout"));
        wait = new WebDriverWait(driver, timeOutInSeconds, 50);
    }

    @AfterGroups(groups = Acceptance)
    public final void destroyDriver() {
        driver.quit();
    }

    private void loadAcceptanceProperties() throws IOException {
        final InputStream resource = getClass().getClassLoader().getResourceAsStream("acceptance.properties");
        acceptanceProperties = new Properties();
        acceptanceProperties.load(resource);
    }

    protected <T extends AbstractPage> T getPage(final String pageUrl, final Class<T> pageClass) {
        final T page = newInstance(pageClass);
        page.init(wait);
        driver.get(String.format("http://%s:%d%s%s",
                acceptanceProperties.getProperty("application.http.host"),
                parseInt(acceptanceProperties.getProperty("application.http.port")),
                acceptanceProperties.getProperty("contextPath"),
                pageUrl));
        return page;
    }

    private <T> T newInstance(final Class<T> pageClass) {
        try {
            return pageClass.newInstance();
        } catch (InstantiationException e) {
            throw new AssertionError(e);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    private WebDriver htmlUnitDriver() {
        return new HtmlUnitDriver(true) {
            @Override
            protected WebClient modifyWebClient(final WebClient client) {
                client.setAlertHandler(new AlertHandler() {
                    public void handleAlert(final Page page, final String message) {
                        Assert.fail(message);
                    }
                });
                return super.modifyWebClient(client);
            }
        };
    }
}
