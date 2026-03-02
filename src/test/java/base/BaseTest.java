package base;
/*
كود الريكورد
mvn exec:java -e "-Dexec.mainClass=com.microsoft.playwright.CLI" "-Dexec.args=codegen https://saas.staging.asis.blue-technologies.tech/en/login"
 */
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;
import Utils.ConfigReader;
import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100));
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1806, 800)
                .setIgnoreHTTPSErrors(true));
        page = context.newPage();
        page.navigate(ConfigReader.getProperty("url"));
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            if (page != null && !page.isClosed()) {
                String testName = result.getName();
                String screenshotPath = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
                byte[] screenshotData = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true));

                saveScreenshotToAllure(screenshotData);

                System.out.println("❌ Test Failed! Screenshot saved at: " + screenshotPath);
            }
        }
        if (context != null) context.close();
    }


    @Attachment(value = "Failure Screenshot", type = "image/png")
    public void saveScreenshotToAllure(byte[] screenshot) {
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}