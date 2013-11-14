package org.dwr.aggregation.webapp.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPage<P extends AbstractPage> {
    private Wait<WebDriver> wait;

    public void init(final WebDriver driver) {
        wait = new WebDriverWait(driver, 10, 50);
    }

    public P andNow(final Verifier<P> verifier) {
        verifier.verify((P) this);
        return (P) this;
    }

    protected <T> T waitFor(final ExpectedCondition<T> condition) {
        return wait.until(condition);
    }
}
