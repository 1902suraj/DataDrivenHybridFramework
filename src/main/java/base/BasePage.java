package base;

import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BasePage {
    protected WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }
}
