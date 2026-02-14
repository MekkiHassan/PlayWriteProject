package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Attachment; // لازم تضيف الـ Import ده
import org.testng.ITestResult;
import org.testng.annotations.*;
import Utils.ConfigReader;
import java.nio.file.Paths;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page; // شلنا الـ static عشان الـ Thread Safety

    @BeforeClass(alwaysRun = true)
    public void setupClass() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)
                .setSlowMo(100)); // 500 كتير شوية، 100 كفاية تخليك تشوف اللي بيحصل
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
            if (page != null && !page.isClosed()) { // تأمين قبل أخذ الصورة
                // 1. حفظ الصورة محلياً (زي ما أنت عامل)
                String testName = result.getName();
                String screenshotPath = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
                byte[] screenshotData = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get(screenshotPath))
                        .setFullPage(true));

                // 2. إرسال الصورة لتقرير Allure (الخطوة السحرية)
                saveScreenshotToAllure(screenshotData);

                System.out.println("❌ Test Failed! Screenshot saved at: " + screenshotPath);
            }
        }
        // قفل الـ context والـ page لكل تست عشان تبدأ "نضيف" في التست اللي بعده
        if (context != null) context.close();
    }

    // الميثود دي هي اللي بتخلي الصورة تظهر في المتصفح جوه Allure
    @Attachment(value = "Failure Screenshot", type = "image/png")
    public void saveScreenshotToAllure(byte[] screenshot) {
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}