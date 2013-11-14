package org.dwr.aggregation.webapp.tests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;

public abstract class AbstractPage<P extends AbstractPage> {
    private Wait<WebDriver> wait;

    public final void init(final Wait<WebDriver> wait) {
        this.wait = wait;
    }

    public final P andNow(final Verifier<P> verifier) {
        verifier.verify((P) this);
        return (P) this;
    }

    protected final <T> T waitFor(final ExpectedCondition<T> condition) {
        return wait.until(condition);
    }
}
