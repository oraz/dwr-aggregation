package org.dwr.aggregation.webapp.tests.pages;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class WebElementConverters {
    public static List<String> textFrom(final List<WebElement> webElements) {
        final List<String> texts = new ArrayList<String>();
        for (final WebElement each : webElements) {
            texts.add(each.getText());
        }
        return texts;
    }
}
