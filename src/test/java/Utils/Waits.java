package Utils;

import com.microsoft.playwright.Locator;

public class Waits {
    public static void waitElement(Locator locator){
        locator.waitFor(new Locator.WaitForOptions().setTimeout(120000));
    }
}
