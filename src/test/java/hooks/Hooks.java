package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.DriverFactory;
import utils.ScreenshotUtil;
import reports.ExtentManager;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Hooks {
    @Before
    public void beforeScenario(Scenario scenario) {
        DriverFactory.initDriver();
        ExtentManager.createTest(scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName());
            ExtentManager.getTest().fail("Scenario failed",
                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
        }
        DriverFactory.quitDriver();
        ExtentManager.getInstance().flush();
    }
}
