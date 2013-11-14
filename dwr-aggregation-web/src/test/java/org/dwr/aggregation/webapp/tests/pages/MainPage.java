package org.dwr.aggregation.webapp.tests.pages;

import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.dwr.aggregation.webapp.tests.pages.WebElementConverters.textFrom;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;

public class MainPage extends AbstractPage<MainPage> {
    public static Verifier<MainPage> userList(final Matcher<Iterable<? extends String>> matcher) {
        return new Verifier<MainPage>() {
            public void verify(final MainPage page) {
                final List<WebElement> users = page.waitFor(visibilityOfAllElementsLocatedBy(cssSelector("#users > li")));

                assertThat(textFrom(users), matcher);
            }
        };
    }

    public static Verifier<MainPage> citiesList(final Matcher<Iterable<? extends String>> matcher) {
        return new Verifier<MainPage>() {
            public void verify(final MainPage page) {
                final List<WebElement> cities = page.waitFor(visibilityOfAllElementsLocatedBy(cssSelector("#cities > li")));

                assertThat(textFrom(cities), matcher);
            }
        };
    }
}
